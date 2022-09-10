package com.github.zharovvv.kmemes.di.ui

import com.github.zharovvv.kmemes.ui.host.MainViewModel
import com.github.zharovvv.kmemes.ui.settings.SettingsActor
import com.github.zharovvv.kmemes.ui.settings.SettingsInitialStateProvider
import com.github.zharovvv.kmemes.ui.settings.SettingsStateReducer
import com.github.zharovvv.kmemes.ui.settings.SettingsViewModel
import org.koin.dsl.module

//порядок объявления переменных имеет значение ¯\_(ツ)_/¯
//если объявить сначала uiModule, а потом viewModelModule, то будет ошибка:
//Caused by: java.lang.IllegalStateException: Flatten - No head element in list
//        at org.koin.core.module.ModuleKt.flatten(Module.kt:245)
val viewModelModule = module {
    factory { MainViewModel(appThemeRepository = get()) }
    factory {
        SettingsViewModel(
            initialStateProvider = SettingsInitialStateProvider(get()),
            reducer = SettingsStateReducer(),
            actor = SettingsActor(appThemeRepository = get()),
            coroutineDispatchers = get()
        )
    }
}

val uiModule = module {
    includes(listOf(viewModelModule))
}