package com.github.zharovvv.kmemes.ui.settings

import com.github.zharovvv.kmemes.core.architecture.elm.model.Result
import com.github.zharovvv.kmemes.core.architecture.elm.store.StateReducer
import com.github.zharovvv.kmemes.model.ui.settings.SettingsAction
import com.github.zharovvv.kmemes.model.ui.settings.SettingsCommand
import com.github.zharovvv.kmemes.model.ui.settings.SettingsEffect
import com.github.zharovvv.kmemes.model.ui.settings.SettingsState

class SettingsStateReducer :
    StateReducer<SettingsAction, SettingsState, SettingsEffect, SettingsCommand> {

    override fun reduce(
        event: SettingsAction,
        state: SettingsState
    ): Result<SettingsState, SettingsEffect, SettingsCommand> {
        return when (event) {
            is SettingsAction.Ui -> reduceUiEvent(event, state)
            is SettingsAction.Internal -> reduceInternalEvent(event, state)
        }
    }

    private fun reduceUiEvent(
        uiEvent: SettingsAction.Ui,
        state: SettingsState
    ): Result<SettingsState, SettingsEffect, SettingsCommand> {
        return when (uiEvent) {
            SettingsAction.Ui.CollapseThemeSection -> Result(
                state = state.copy(expandedThemeSection = false)
            )
            SettingsAction.Ui.ExpandThemeSection -> Result(
                state = state.copy(expandedThemeSection = true)
            )
            is SettingsAction.Ui.ClickChangeTheme -> Result(
                state = state,
                commands = listOf(
                    SettingsCommand.ChangeTheme(
                        selectedThemeModeItem = uiEvent.selectedThemeModeItem
                    )
                )
            )
            is SettingsAction.Ui.ClickChangeColorScheme -> Result(
                state = state,
                commands = listOf(
                    SettingsCommand.ChangeColorScheme(useDynamic = uiEvent.useDynamic)
                )
            )
        }
    }

    private fun reduceInternalEvent(
        internalEvent: SettingsAction.Internal,
        state: SettingsState
    ): Result<SettingsState, SettingsEffect, SettingsCommand> {
        return when (internalEvent) {
            is SettingsAction.Internal.ColorSchemeChanged -> Result(
                state = state.copy(useDynamicColors = internalEvent.useDynamic),
                effect = SettingsEffect.ShowToast(
                    if (internalEvent.useDynamic) {
                        "Применен динамический цвет"
                    } else {
                        "Динамический цвет отключен"
                    }
                )
            )
            is SettingsAction.Internal.ThemeChanged -> Result(
                state = state.copy(
                    selectedThemeModeItem = internalEvent.selectedThemeModeItem,
                    nonSelectedThemeModeItems = internalEvent.nonSelectedThemeModeItems,
                    expandedThemeSection = false
                )
            )
        }
    }
}