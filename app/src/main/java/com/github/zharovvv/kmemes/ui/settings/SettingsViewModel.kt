package com.github.zharovvv.kmemes.ui.settings

import com.github.zharovvv.kmemes.core.architecture.elm.optin.DelicateElmViewModelConstructor
import com.github.zharovvv.kmemes.core.architecture.elm.ui.ElmViewModel
import com.github.zharovvv.kmemes.core.coroutines.CoroutineDispatchers
import com.github.zharovvv.kmemes.model.ui.settings.SettingsAction
import com.github.zharovvv.kmemes.model.ui.settings.SettingsCommand
import com.github.zharovvv.kmemes.model.ui.settings.SettingsEffect
import com.github.zharovvv.kmemes.model.ui.settings.SettingsState

@OptIn(DelicateElmViewModelConstructor::class)
class SettingsViewModel(
    initialStateProvider: SettingsInitialStateProvider,
    reducer: SettingsStateReducer,
    actor: SettingsActor,
    coroutineDispatchers: CoroutineDispatchers
) : ElmViewModel<SettingsAction, SettingsState, SettingsEffect, SettingsCommand>(
    initialStateProvider::initialState,
    reducer,
    actor,
    coroutineDispatchers
)