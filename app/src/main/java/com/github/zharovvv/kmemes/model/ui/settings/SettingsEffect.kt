package com.github.zharovvv.kmemes.model.ui.settings

//TODO Добавлено для примера
sealed class SettingsEffect {
    class ShowToast(val toastText: String) : SettingsEffect()
}
