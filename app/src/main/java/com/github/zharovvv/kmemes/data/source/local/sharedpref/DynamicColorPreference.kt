package com.github.zharovvv.kmemes.data.source.local.sharedpref

import android.content.SharedPreferences
import com.github.zharovvv.kmemes.data.source.local.sharedpref.base.BooleanPreferenceStorage

class DynamicColorPreference(
    sharedPreferences: SharedPreferences
) : BooleanPreferenceStorage(sharedPreferences) {

    override val key: String = PreferenceKeys.DynamicColor
    override val default: Boolean = false
}