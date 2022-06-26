package com.github.zharovvv.kmemes.ui.host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zharovvv.kmemes.model.data.source.local.sharedpref.ThemeMode
import com.github.zharovvv.kmemes.ui.settings.SettingsViewModel
import com.github.zharovvv.kmemes.ui.theme.KMemesAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModel()

    //TODO работает коряво
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appTheme by remember { settingsViewModel.appThemeState }
            KMemesAppTheme(
                appTheme = appTheme
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
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
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KMemesAppTheme {
        Greeting("Android")
    }
}