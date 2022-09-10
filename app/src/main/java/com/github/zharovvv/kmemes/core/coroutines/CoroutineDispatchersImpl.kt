package com.github.zharovvv.kmemes.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatchersImpl : CoroutineDispatchers {

    override val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    override val mainDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
    override val computationDispatcher: CoroutineDispatcher = Dispatchers.Default
}