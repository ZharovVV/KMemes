package com.github.zharovvv.kmemes.ui.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.zharovvv.kmemes.domain.AppThemeRepository
import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode
import com.github.zharovvv.kmemes.model.domain.AppTheme
import kotlinx.coroutines.*

class SettingsViewModel(
    private val appThemeRepository: AppThemeRepository
) : ViewModel(), CoroutineScope by MainScope() {

    private val _appThemeState: MutableState<AppTheme> =
        //TODO почитать как правильно - сейчас коряво
        mutableStateOf(
            AppTheme(
                themeMode = ThemeMode.SYSTEM,
                useDynamicColors = false
            )
        )
    val appThemeState: State<AppTheme> = _appThemeState

    init {
        launch {
            _appThemeState.value = withContext(Dispatchers.IO) {
                appThemeRepository.getAppTheme()
            }
            appThemeRepository.observeChanges()
                .collect { _appThemeState.value = it }
        }
    }

    fun update(appTheme: AppTheme) {
        launch {
            appThemeRepository.updateAppTheme(appTheme)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancel()
    }

}