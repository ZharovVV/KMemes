package com.github.zharovvv.kmemes.model.ui.settings

import androidx.compose.runtime.Stable
import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode

@Stable
data class SettingsState(
    val themeMode: ThemeMode,
    val useDynamicColors: Boolean
)
