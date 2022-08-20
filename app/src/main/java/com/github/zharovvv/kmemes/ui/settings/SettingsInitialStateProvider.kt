package com.github.zharovvv.kmemes.ui.settings

import com.github.zharovvv.kmemes.domain.AppThemeRepository
import com.github.zharovvv.kmemes.model.domain.AppTheme
import com.github.zharovvv.kmemes.model.ui.settings.SettingsState

class SettingsInitialStateProvider(
    private val appThemeRepository: AppThemeRepository
) {

    val initialState: SettingsState
        get() {
            val appTheme: AppTheme = appThemeRepository.getAppThemeBlocking()
            return SettingsState(
                themeMode = appTheme.themeMode,
                useDynamicColors = appTheme.useDynamicColors
            )
        }
}