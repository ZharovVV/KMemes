package com.github.zharovvv.kmemes.model.ui.settings

import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode

sealed interface SettingsAction {

    sealed class Ui : SettingsAction {
        object Initialize : Ui()
        data class ClickChangeTheme(val selectedThemeMode: ThemeMode) : Ui()
        data class ClickChangeColorScheme(val useDynamic: Boolean) : Ui()
    }

    sealed class Internal : SettingsAction {
        data class ThemeChanged(val changedThemeMode: ThemeMode) : Internal()
        data class ColorSchemeChanged(val useDynamic: Boolean) : Internal()
    }
}
