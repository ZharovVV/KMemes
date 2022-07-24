package com.github.zharovvv.kmemes.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode
import com.github.zharovvv.kmemes.ui.host.Greeting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    // A surface container using the 'background' color from the theme
    val appTheme by settingsViewModel.appTheme.collectAsState()
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Greeting("Android")
            Switch(
                checked = appTheme.useDynamicColors,
                onCheckedChange = {
                    settingsViewModel.update(appTheme.copy(useDynamicColors = it))
                }
            )
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
                        color = MaterialTheme.colorScheme.onBackground
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
                    Text(text = "LIGHT")
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
                    Text(text = "DARK")
                }
            }
        }
    }
}