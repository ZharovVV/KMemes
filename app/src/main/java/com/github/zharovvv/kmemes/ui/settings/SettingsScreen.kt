package com.github.zharovvv.kmemes.ui.settings

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zharovvv.kmemes.model.ui.settings.SettingsAction
import com.github.zharovvv.kmemes.model.ui.settings.SettingsState
import com.github.zharovvv.kmemes.model.ui.settings.ThemeModeItem
import com.github.zharovvv.kmemes.model.ui.settings.themeName
import com.github.zharovvv.kmemes.ui.navigation.composition.common.ExpandableGroup
import com.github.zharovvv.kmemes.ui.navigation.composition.common.TextRadioButton
import com.github.zharovvv.kmemes.ui.theme.ThemedPreview

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    val settingsState by settingsViewModel.states.collectAsState()
    Log.i("KMemes-Debug", "screen: $settingsState")
    SettingsScreen(
        settingsState = settingsState,
        onChangeColorScheme = { useDynamicColor ->
            settingsViewModel.accept(SettingsAction.Ui.ClickChangeColorScheme(useDynamicColor))
        },
        onClickThemeSection = { expanded ->
            settingsViewModel.accept(
                if (expanded) {
                    SettingsAction.Ui.CollapseThemeSection
                } else {
                    SettingsAction.Ui.ExpandThemeSection
                }
            )
        },
        onChangeThemeMode = { themeModeItem ->
            settingsViewModel.accept(SettingsAction.Ui.ClickChangeTheme(themeModeItem))
        }
    )
}

@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onChangeColorScheme: (useDynamicColor: Boolean) -> Unit,
    onClickThemeSection: (expanded: Boolean) -> Unit,
    onChangeThemeMode: (themeModeItem: ThemeModeItem) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        tonalElevation = 3.dp
    ) {
        Column {
            Text(
                text = "Настройки",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            ExpandableGroup(
                expanded = settingsState.expandedThemeSection,
                expandedContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        settingsState.nonSelectedThemeModeItems.forEach { item ->
                            TextRadioButton(text = item.themeName, selected = false, onClick = {
                                onChangeThemeMode.invoke(item)
                            })
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClickThemeSection.invoke(settingsState.expandedThemeSection)
                    }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Тема приложения",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f, true))
                    TextRadioButton(
                        text = settingsState.selectedThemeModeItem.themeName,
                        selected = true,
                        onClick = {}
                    )
                }
            }
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val haptic = LocalHapticFeedback.current
                Text(
                    text = "Динамический цвет",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f, true))
                Switch(
                    checked = settingsState.useDynamicColors,
                    onCheckedChange = { checked ->
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onChangeColorScheme.invoke(checked)
                    }
                )
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
                selectedThemeModeItem = ThemeModeItem.SYSTEM,
                nonSelectedThemeModeItems = listOf(ThemeModeItem.LIGHT, ThemeModeItem.DARK),
                expandedThemeSection = true,
                useDynamicColors = false
            ),
            onChangeColorScheme = {},
            onClickThemeSection = {},
            onChangeThemeMode = {}
        )
    }
}
