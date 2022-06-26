package com.github.zharovvv.kmemes.data.source.local.sharedpref.base

import android.content.SharedPreferences

abstract class BooleanPreferenceStorage(
    sharedPreferences: SharedPreferences
) : BasePreferenceStorage<Boolean>(sharedPreferences) {

    override fun SharedPreferences.getUnsafe(): Boolean = getBoolean(key, default)

    override fun SharedPreferences.Editor.setUnsafe(value: Boolean) {
        putBoolean(key, value)
    }
}