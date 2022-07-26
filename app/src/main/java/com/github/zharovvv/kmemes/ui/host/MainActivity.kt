package com.github.zharovvv.kmemes.ui.host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.zharovvv.kmemes.di.koinViewModels
import com.github.zharovvv.kmemes.ui.navigation.RootNavGraph
import com.github.zharovvv.kmemes.ui.theme.KMemesAppTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by koinViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appTheme by mainViewModel.appTheme.collectAsState()
            KMemesAppTheme(appTheme = appTheme) {
                RootNavGraph()
            }
        }
    }
}