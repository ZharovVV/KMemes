package com.github.zharovvv.kmemes.ui.settings

import com.github.zharovvv.kmemes.core.architecture.elm.model.Result
import com.github.zharovvv.kmemes.core.architecture.elm.store.StateReducer
import com.github.zharovvv.kmemes.model.ui.settings.SettingsAction
import com.github.zharovvv.kmemes.model.ui.settings.SettingsCommand
import com.github.zharovvv.kmemes.model.ui.settings.SettingsState

class SettingsStateReducer : StateReducer<SettingsAction, SettingsState, Nothing, SettingsCommand> {

    override fun reduce(
        event: SettingsAction,
        state: SettingsState
    ): Result<SettingsState, Nothing, SettingsCommand> {
        return when (event) {
            SettingsAction.Ui.Initialize -> Result(state)
            is SettingsAction.Ui.ClickChangeTheme -> Result(
                state = state.copy(themeMode = event.selectedThemeMode),
                commands = listOf(
                    SettingsCommand.ChangeTheme(
                        selectedThemeMode = event.selectedThemeMode
                    )
                )
            )
            is SettingsAction.Ui.ClickChangeColorScheme -> Result(
                state = state.copy(useDynamicColors = event.useDynamic),
                commands = listOf(
                    SettingsCommand.ChangeColorScheme(useDynamic = event.useDynamic)
                )
            )
            is SettingsAction.Internal.ColorSchemeChanged -> Result(state)
            is SettingsAction.Internal.ThemeChanged -> Result(state)
        }
    }
}