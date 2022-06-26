package com.github.zharovvv.kmemes.domain

import com.github.zharovvv.kmemes.model.domain.AppTheme
import kotlinx.coroutines.flow.Flow

interface AppThemeRepository {

    suspend fun getAppTheme(): AppTheme

    suspend fun updateAppTheme(appTheme: AppTheme)

    fun observeChanges(): Flow<AppTheme>
}