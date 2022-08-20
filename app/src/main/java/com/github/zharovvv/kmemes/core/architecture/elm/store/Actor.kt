package com.github.zharovvv.kmemes.core.architecture.elm.store

/**
 * **Actor**
 * Отвечает за:
 * * Получение данных
 * * Команды модели
 * * Вычисления
 */
interface Actor<in Command : Any, out Event : Any> {

    suspend fun execute(command: Command): Event
}