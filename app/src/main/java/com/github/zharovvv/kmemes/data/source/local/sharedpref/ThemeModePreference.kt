package com.github.zharovvv.kmemes.data.source.local.sharedpref

import android.content.SharedPreferences
import com.github.zharovvv.kmemes.data.source.local.sharedpref.base.BasePreferenceStorage
import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode

class ThemeModePreference(
    sharedPreferences: SharedPreferences
) : BasePreferenceStorage<ThemeMode>(sharedPreferences) {

    private val themeModeMap: Map<Int, ThemeMode> = ThemeMode.values().associateBy { it.sourceCode }

    override val key: String = PreferenceKeys.ThemeMode
    override val default: ThemeMode = ThemeMode.SYSTEM

    override fun SharedPreferences.getUnsafe(): ThemeMode =
        themeModeMap.getValue(getInt(key, default.sourceCode))

    override fun SharedPreferences.Editor.setUnsafe(value: ThemeMode) {
        putInt(key, value.sourceCode)
    }
}