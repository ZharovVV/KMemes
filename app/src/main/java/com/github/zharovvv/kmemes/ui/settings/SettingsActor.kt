package com.github.zharovvv.kmemes.ui.settings

import com.github.zharovvv.kmemes.core.architecture.elm.store.Actor
import com.github.zharovvv.kmemes.domain.AppThemeRepository
import com.github.zharovvv.kmemes.model.ui.settings.SettingsAction
import com.github.zharovvv.kmemes.model.ui.settings.SettingsCommand
import com.github.zharovvv.kmemes.model.ui.settings.ThemeModeItem

class SettingsActor(
    private val appThemeRepository: AppThemeRepository
) : Actor<SettingsCommand, SettingsAction.Internal> {

    override suspend fun execute(command: SettingsCommand): SettingsAction.Internal {
        return when (command) {
            is SettingsCommand.ChangeColorScheme -> {
                appThemeRepository.updateColorScheme(command.useDynamic)
                SettingsAction.Internal.ColorSchemeChanged(command.useDynamic)
            }
            is SettingsCommand.ChangeTheme -> {
                appThemeRepository.updateThemeMode(command.selectedThemeModeItem.themeMode)
                SettingsAction.Internal.ThemeChanged(
                    command.selectedThemeModeItem,
                    computeNonSelectedThemeModeItems(command.selectedThemeModeItem)
                )
            }
        }
    }

    //TODO вынести в класс
    private fun computeNonSelectedThemeModeItems(selectedThemeModeItem: ThemeModeItem): List<ThemeModeItem> {
        return ThemeModeItem.values().filter { it != selectedThemeModeItem }.toList()
    }
}