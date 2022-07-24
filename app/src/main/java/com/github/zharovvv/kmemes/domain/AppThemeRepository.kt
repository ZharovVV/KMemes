package com.github.zharovvv.kmemes.domain

import com.github.zharovvv.kmemes.model.domain.AppTheme
import kotlinx.coroutines.flow.Flow

interface AppThemeRepository {

    fun getAppThemeBlocking(): AppTheme

    suspend fun getAppTheme(): AppTheme

    suspend fun updateAppTheme(appTheme: AppTheme)

    fun appThemeFlow(): Flow<AppTheme>
}