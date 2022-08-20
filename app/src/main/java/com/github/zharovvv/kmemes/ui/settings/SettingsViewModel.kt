package com.github.zharovvv.kmemes.ui.settings

import com.github.zharovvv.kmemes.core.architecture.elm.optin.DelicateElmViewModelConstructor
import com.github.zharovvv.kmemes.core.architecture.elm.ui.ElmViewModel
import com.github.zharovvv.kmemes.model.ui.settings.SettingsAction
import com.github.zharovvv.kmemes.model.ui.settings.SettingsCommand
import com.github.zharovvv.kmemes.model.ui.settings.SettingsState

@OptIn(DelicateElmViewModelConstructor::class)
class SettingsViewModel(
    initialStateProvider: SettingsInitialStateProvider,
    reducer: SettingsStateReducer,
    actor: SettingsActor
) : ElmViewModel<SettingsAction, SettingsState, Nothing, SettingsCommand>(
    initialStateProvider::initialState,
    reducer,
    actor
)