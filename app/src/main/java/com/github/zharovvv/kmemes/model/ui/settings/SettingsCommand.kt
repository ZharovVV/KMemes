package com.github.zharovvv.kmemes.model.ui.settings

import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode

sealed class SettingsCommand {
    data class ChangeTheme(val selectedThemeMode: ThemeMode) : SettingsCommand()
    data class ChangeColorScheme(val useDynamic: Boolean) : SettingsCommand()
}
