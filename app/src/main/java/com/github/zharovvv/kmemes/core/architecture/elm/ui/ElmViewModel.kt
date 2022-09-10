package com.github.zharovvv.kmemes.core.architecture.elm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.zharovvv.kmemes.core.architecture.elm.model.Result
import com.github.zharovvv.kmemes.core.architecture.elm.optin.DelicateElmViewModelConstructor
import com.github.zharovvv.kmemes.core.architecture.elm.store.Actor
import com.github.zharovvv.kmemes.core.architecture.elm.store.StateReducer
import com.github.zharovvv.kmemes.core.architecture.elm.store.Store
import com.github.zharovvv.kmemes.core.coroutines.CoroutineDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

open class ElmViewModel<Event : Any, State : Any, Effect : Any, Command : Any>(
    initialState: State,
    private val reducer: StateReducer<Event, State, Effect, Command>,
    private val actor: Actor<Command, Event>,
    private val coroutineDispatchers: CoroutineDispatchers
) : ViewModel(), Store<Event, Effect, State> {

    @DelicateElmViewModelConstructor
    constructor(
        initialStateProvider: () -> State,
        reducer: StateReducer<Event, State, Effect, Command>,
        actor: Actor<Command, Event>,
        coroutineDispatchers: CoroutineDispatchers
    ) : this(
        initialStateProvider.invoke(),
        reducer,
        actor,
        coroutineDispatchers
    )

    private val _states: MutableStateFlow<State> = MutableStateFlow(initialState)
    final override val states: StateFlow<State> get() = _states
    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    final override val effects: Flow<Effect> get() = _effects

    private val _uiEvents = MutableSharedFlow<Event>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val _internalEvents = MutableSharedFlow<Event>()
    private val events: Flow<Event> = merge(_uiEvents, _internalEvents)

    init {
        //combine тут не подойдет, а withLatestFrom в корутинах нет
        events
            .map { event: Event ->
                val currentState: State = _states.value
                reducer.reduce(event, currentState)
            }
            .onEach { result: Result<State, Effect, Command> ->
                dispatchState(result.state)
                result.effect?.let { effect: Effect ->
                    dispatchEffect(effect)
                }
                result.commands?.let(::executeCommands)
            }
            .launchIn(viewModelScope)
    }

    final override fun accept(event: Event) {
        dispatchUiEvent(event)
    }

    private fun dispatchUiEvent(event: Event) {
        _uiEvents.tryEmit(event)
    }

    private suspend fun dispatchInternalEvent(event: Event) {
        _internalEvents.emit(event)
    }

    private fun dispatchState(state: State) {
        _states.tryEmit(state)
    }

    private suspend fun dispatchEffect(effect: Effect) {
        _effects.emit(effect)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun executeCommands(commands: List<Command>) {
        commands.asFlow()
            .map { command: Command -> actor.execute(command) }
            //Такой Dispatcher нужно всегда закрывать, когда он больше не нужен
//            .flowOn(newSingleThreadContext(name = "ElmStoreDispatcher"))
            //Данное решение гораздо лучше
            .flowOn(
                coroutineDispatchers.computationDispatcher
                    .limitedParallelism(1)
            )
            .onEach(::dispatchInternalEvent)
            .launchIn(viewModelScope)
    }

}