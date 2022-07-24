package com.github.zharovvv.kmemes.ui.host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
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