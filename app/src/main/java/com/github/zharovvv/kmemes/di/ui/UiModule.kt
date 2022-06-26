package com.github.zharovvv.kmemes.di.ui

import com.github.zharovvv.kmemes.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        SettingsViewModel(appThemeRepository = get())
    }
}