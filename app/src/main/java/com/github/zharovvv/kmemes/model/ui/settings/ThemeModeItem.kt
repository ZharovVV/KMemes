package com.github.zharovvv.kmemes.model.ui.settings

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import com.github.zharovvv.kmemes.R
import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode

@Stable
enum class ThemeModeItem(
    @StringRes
    val nameRes: Int,
    val themeMode: ThemeMode
) {
    SYSTEM(R.string.system_theme_name, ThemeMode.SYSTEM),
    LIGHT(R.string.light_theme_name, ThemeMode.LIGHT),
    DARK(R.string.dark_theme_name, ThemeMode.DARK)
}

val ThemeModeItem.themeName: String
    @Composable
    @ReadOnlyComposable
    get() = stringResource(id = nameRes)

private val themeModeMap: Map<ThemeMode, ThemeModeItem> =
    ThemeModeItem.values().associateBy { it.themeMode }

fun ThemeMode.toItem(): ThemeModeItem = themeModeMap[this]
    ?: throw NoSuchElementException("could not find ThemeModeItem associated with $this")