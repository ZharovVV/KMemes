package com.github.zharovvv.kmemes.core.architecture.elm.store

import com.github.zharovvv.kmemes.core.architecture.elm.model.Result

/**
 * **Reducer**
 * Отвечает за:
 * * Обработку событий
 * * Изменение состояния
 * * Команды UI
 */
interface StateReducer<Event : Any, State : Any, Effect : Any, Command : Any> {

    fun reduce(event: Event, state: State): Result<State, Effect, Command>
}