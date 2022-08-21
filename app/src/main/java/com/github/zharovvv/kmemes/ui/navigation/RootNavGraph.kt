package com.github.zharovvv.kmemes.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavGraph() {
    val rootNavController = rememberNavController()
    NavHost(
        navController = rootNavController,
        startDestination = Routes.main,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        route = Routes.root,
    ) {
        composable(Routes.main) { MainNavGraph(rootNavController = rootNavController) }
    }
}

