package com.github.zharovvv.kmemes.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.zharovvv.kmemes.domain.AppThemeRepository
import com.github.zharovvv.kmemes.model.domain.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val appThemeRepository: AppThemeRepository
) : ViewModel() {

    private val _appTheme: MutableStateFlow<AppTheme> = MutableStateFlow(
        appThemeRepository.getAppThemeBlocking()
    )
    val appTheme: StateFlow<AppTheme> = _appTheme

    init {
        appThemeRepository.appThemeFlow()
            .onEach(_appTheme::emit)
            .launchIn(viewModelScope)
    }

    fun update(appTheme: AppTheme) {
        viewModelScope.launch {
            appThemeRepository.updateAppTheme(appTheme)
        }
    }
}