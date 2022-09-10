package com.github.zharovvv.kmemes.di.core

import com.github.zharovvv.kmemes.core.coroutines.CoroutineDispatchers
import com.github.zharovvv.kmemes.core.coroutines.CoroutineDispatchersImpl
import org.koin.dsl.module

val coreModule = module {
    single<CoroutineDispatchers> { CoroutineDispatchersImpl() }
}