package com.github.zharovvv.kmemes.data.repository

import com.github.zharovvv.kmemes.data.source.local.sharedpref.DynamicColorPreference
import com.github.zharovvv.kmemes.data.source.local.sharedpref.ThemeModePreference
import com.github.zharovvv.kmemes.domain.AppThemeRepository
import com.github.zharovvv.kmemes.model.domain.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class AppThemeRepositoryImpl(
    private val dynamicColorPreference: DynamicColorPreference,
    private val themeModePreference: ThemeModePreference
) : AppThemeRepository {

    override fun getAppThemeBlocking(): AppTheme {
        return AppTheme(
            themeMode = themeModePreference.getBlocking(),
            useDynamicColors = dynamicColorPreference.getBlocking()
        )
    }

    override suspend fun getAppTheme(): AppTheme {
        return AppTheme(
            themeMode = themeModePreference.get(),
            useDynamicColors = dynamicColorPreference.get()
        )
    }

    override suspend fun updateAppTheme(appTheme: AppTheme) {
        themeModePreference.set(appTheme.themeMode)
        dynamicColorPreference.set(appTheme.useDynamicColors)
    }

    override fun appThemeFlow(): Flow<AppTheme> = combine(
        themeModePreference.preferenceFlow(),
        dynamicColorPreference.preferenceFlow()
    ) { themeMode, useDynamicColor ->
        AppTheme(themeMode, useDynamicColor)
    }
}