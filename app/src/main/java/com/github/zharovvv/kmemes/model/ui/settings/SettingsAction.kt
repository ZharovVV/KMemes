package com.github.zharovvv.kmemes.model.ui.settings

sealed interface SettingsAction {

    sealed class Ui : SettingsAction {
        object Initialize : Ui()
        object ExpandThemeSection : Ui()
        object CollapseThemeSection : Ui()
        data class ClickChangeTheme(val selectedThemeModeItem: ThemeModeItem) : Ui()
        data class ClickChangeColorScheme(val useDynamic: Boolean) : Ui()
    }

    sealed class Internal : SettingsAction {
        data class ThemeChanged(
            val selectedThemeModeItem: ThemeModeItem,
            val nonSelectedThemeModeItems: List<ThemeModeItem>
        ) : Internal()

        data class ColorSchemeChanged(val useDynamic: Boolean) : Internal()
    }
}
