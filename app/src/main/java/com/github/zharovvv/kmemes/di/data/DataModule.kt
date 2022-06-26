package com.github.zharovvv.kmemes.di.data

import android.content.Context
import com.github.zharovvv.kmemes.data.repository.AppThemeRepositoryImpl
import com.github.zharovvv.kmemes.data.source.local.sharedpref.DynamicColorPreference
import com.github.zharovvv.kmemes.data.source.local.sharedpref.ThemeModePreference
import com.github.zharovvv.kmemes.domain.AppThemeRepository
import org.koin.dsl.module

val dataModule = module {
    single { get<Context>().getSharedPreferences("kmemes_preferences", Context.MODE_PRIVATE) }
    single { DynamicColorPreference(get()) }
    single { ThemeModePreference(get()) }
    single<AppThemeRepository> {
        AppThemeRepositoryImpl(
            dynamicColorPreference = get(),
            themeModePreference = get()
        )
    }
}