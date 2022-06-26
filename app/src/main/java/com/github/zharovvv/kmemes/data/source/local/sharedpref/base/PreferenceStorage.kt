package com.github.zharovvv.kmemes.data.source.local.sharedpref.base

import kotlinx.coroutines.flow.Flow

interface PreferenceStorage<T : Any?> {

    suspend fun get(): T

    suspend fun set(value: T)

    fun observeChanges(): Flow<T>
}