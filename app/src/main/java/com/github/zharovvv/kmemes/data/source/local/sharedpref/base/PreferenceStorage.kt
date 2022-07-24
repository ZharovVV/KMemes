package com.github.zharovvv.kmemes.data.source.local.sharedpref.base

import kotlinx.coroutines.flow.Flow

interface PreferenceStorage<T : Any?> {

    fun getBlocking(): T

    suspend fun get(): T

    suspend fun set(value: T)

    fun preferenceFlow(): Flow<T>
}