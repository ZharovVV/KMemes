package com.github.zharovvv.kmemes.model.ui.settings

import androidx.compose.runtime.Stable

@Stable
data class SettingsState(
    val selectedThemeModeItem: ThemeModeItem,
    val nonSelectedThemeModeItems: List<ThemeModeItem>,
    val expandedThemeSection: Boolean,
    val useDynamicColors: Boolean
)
