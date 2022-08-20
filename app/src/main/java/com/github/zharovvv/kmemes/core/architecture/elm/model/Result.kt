package com.github.zharovvv.kmemes.core.architecture.elm.model

import com.github.zharovvv.kmemes.core.architecture.elm.store.StateReducer

/**
 * Модель результата функции [StateReducer.reduce]
 * @constructor
 * @property state текущее состояния экрана.
 * @property effect Команды для UI (например показ Snackbar, Toast, переход на другой экран).
 * @property commands команды запуска операций в Actor
 */
data class Result<State : Any, Effect : Any, Command : Any>(
    val state: State,
    val effect: Effect? = null,
    val commands: List<Command>? = null
)