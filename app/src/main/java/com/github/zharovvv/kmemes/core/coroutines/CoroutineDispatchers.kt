package com.github.zharovvv.kmemes.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {

    val ioDispatcher: CoroutineDispatcher

    val mainDispatcher: CoroutineDispatcher

    val computationDispatcher: CoroutineDispatcher
}