package com.github.zharovvv.kmemes.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    // A surface container using the 'background' color from the theme
    val appTheme by settingsViewModel.appTheme.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Настройки", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = appTheme.useDynamicColors,
                    onCheckedChange = {
                        settingsViewModel.update(appTheme.copy(useDynamicColors = it))
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
                        selected = appTheme.themeMode == ThemeMode.SYSTEM,
                        onClick = {
                            settingsViewModel.update(
                                appTheme.copy(themeMode = ThemeMode.SYSTEM)
                            )
                        }
                    )
                    Text(
                        text = "SYSTEM",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = appTheme.themeMode == ThemeMode.LIGHT,
                        onClick = {
                            settingsViewModel.update(
                                appTheme.copy(themeMode = ThemeMode.LIGHT)
                            )
                        }
                    )
                    Text(text = "LIGHT", style = MaterialTheme.typography.bodySmall)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = appTheme.themeMode == ThemeMode.DARK,
                        onClick = {
                            settingsViewModel.update(
                                appTheme.copy(themeMode = ThemeMode.DARK)
                            )
                        }
                    )
                    Text(text = "DARK", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}