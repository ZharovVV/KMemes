package com.github.zharovvv.kmemes.model.ui.settings

sealed class SettingsCommand {
    data class ChangeTheme(val selectedThemeModeItem: ThemeModeItem) : SettingsCommand()
    data class ChangeColorScheme(val useDynamic: Boolean) : SettingsCommand()
}
