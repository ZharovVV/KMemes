package com.github.zharovvv.kmemes.domain

import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode
import com.github.zharovvv.kmemes.model.domain.AppTheme
import kotlinx.coroutines.flow.Flow

interface AppThemeRepository {

    fun getAppThemeBlocking(): AppTheme

    suspend fun getAppTheme(): AppTheme

    suspend fun updateAppTheme(appTheme: AppTheme)

    suspend fun updateColorScheme(useDynamicColors: Boolean)

    suspend fun updateThemeMode(themeMode: ThemeMode)

    fun appThemeFlow(): Flow<AppTheme>
}