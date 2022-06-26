package com.github.zharovvv.kmemes.model.domain

import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode

data class AppTheme(
    val themeMode: ThemeMode,
    val useDynamicColors: Boolean
)


