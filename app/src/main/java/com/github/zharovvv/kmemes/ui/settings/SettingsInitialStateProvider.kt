package com.github.zharovvv.kmemes.ui.settings

import com.github.zharovvv.kmemes.domain.AppThemeRepository
import com.github.zharovvv.kmemes.model.domain.AppTheme
import com.github.zharovvv.kmemes.model.ui.settings.SettingsState
import com.github.zharovvv.kmemes.model.ui.settings.ThemeModeItem
import com.github.zharovvv.kmemes.model.ui.settings.toItem

class SettingsInitialStateProvider(
    private val appThemeRepository: AppThemeRepository
) {

    val initialState: SettingsState
        get() {
            val appTheme: AppTheme = appThemeRepository.getAppThemeBlocking()
            val selectedThemeModeItem = appTheme.themeMode.toItem()
            return SettingsState(
                selectedThemeModeItem = selectedThemeModeItem,
                nonSelectedThemeModeItems = computeNonSelectedThemeModeItems(selectedThemeModeItem),
                expandedThemeSection = false,
                useDynamicColors = appTheme.useDynamicColors
            )
        }

    //TODO вынести в класс
    private fun computeNonSelectedThemeModeItems(selectedThemeModeItem: ThemeModeItem): List<ThemeModeItem> {
        return ThemeModeItem.values().filter { it != selectedThemeModeItem }.toList()
    }
}