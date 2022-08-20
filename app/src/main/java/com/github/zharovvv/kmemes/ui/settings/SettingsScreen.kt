package com.github.zharovvv.kmemes.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode
import com.github.zharovvv.kmemes.model.ui.settings.SettingsAction
import com.github.zharovvv.kmemes.model.ui.settings.SettingsState
import com.github.zharovvv.kmemes.ui.theme.ThemedPreview

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    settingsViewModel.accept(SettingsAction.Ui.Initialize)
    val settingsState by settingsViewModel.states.collectAsState()
    SettingsScreen(
        settingsState = settingsState,
        onChangeColorScheme = { useDynamicColor ->
            settingsViewModel.accept(SettingsAction.Ui.ClickChangeColorScheme(useDynamicColor))
        },
        onChangeThemeMode = { themeMode ->
            settingsViewModel.accept(SettingsAction.Ui.ClickChangeTheme(themeMode))
        }
    )
}

//TODO Это заготовка - переделать
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onChangeColorScheme: (useDynamicColor: Boolean) -> Unit,
    onChangeThemeMode: (themeMode: ThemeMode) -> Unit
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Настройки", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                val haptic = LocalHapticFeedback.current
                Switch(
                    checked = settingsState.useDynamicColors,
                    onCheckedChange = { checked ->
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onChangeColorScheme.invoke(checked)
                    }
                )
                Text(
                    text = "Dynamic Color",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column(Modifier.selectableGroup()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = settingsState.themeMode == ThemeMode.SYSTEM,
                        onClick = {
                            onChangeThemeMode.invoke(ThemeMode.SYSTEM)
                        }
                    )
                    Text(
                        text = "SYSTEM",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = settingsState.themeMode == ThemeMode.LIGHT,
                        onClick = {
                            onChangeThemeMode.invoke(ThemeMode.LIGHT)
                        }
                    )
                    Text(text = "LIGHT", style = MaterialTheme.typography.bodySmall)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = settingsState.themeMode == ThemeMode.DARK,
                        onClick = {
                            onChangeThemeMode.invoke(ThemeMode.DARK)
                        }
                    )
                    Text(text = "DARK", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    ThemedPreview {
        SettingsScreen(
            settingsState = SettingsState(
                themeMode = ThemeMode.SYSTEM,
                useDynamicColors = false
            ),
            onChangeColorScheme = {},
            onChangeThemeMode = {}
        )
    }
}
