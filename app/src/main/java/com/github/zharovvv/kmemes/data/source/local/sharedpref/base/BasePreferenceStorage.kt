package com.github.zharovvv.kmemes.data.source.local.sharedpref.base

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class BasePreferenceStorage<T>(
    private val sharedPreferences: SharedPreferences
) : PreferenceStorage<T> {

    abstract val key: String
    abstract val default: T

    protected abstract fun SharedPreferences.getUnsafe(): T

    protected abstract fun SharedPreferences.Editor.setUnsafe(value: T)

    final override suspend fun get(): T = withContext(Dispatchers.IO) {
        getBlocking()
    }

    private fun getBlocking(): T = runCatching {
        if (sharedPreferences.contains(key)) {
            sharedPreferences.getUnsafe()
        } else {
            default
        }
    }.getOrDefault(default)

    final override suspend fun set(value: T) {
        withContext(Dispatchers.IO) {
            if (value == null) {
                sharedPreferences.edit(commit = true) {
                    remove(key)
                }
            } else {
                runCatching {
                    sharedPreferences.edit(commit = true) {
                        setUnsafe(value)
                    }
                }
            }
        }

    }

    final override fun observeChanges(): Flow<T> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (key == changedKey) {
                trySendBlocking(getBlocking())
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }.flowOn(Dispatchers.IO)
}