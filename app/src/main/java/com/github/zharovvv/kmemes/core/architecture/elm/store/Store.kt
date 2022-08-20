package com.github.zharovvv.kmemes.core.architecture.elm.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Store<Event, Effect, State> {

    val states: StateFlow<State>
    val effects: Flow<Effect>

    fun accept(event: Event)
}