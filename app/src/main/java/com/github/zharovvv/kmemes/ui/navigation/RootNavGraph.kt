package com.github.zharovvv.kmemes.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
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
        startDestination = Graphs.Root.Main.route,
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        route = Graphs.Root.route,
    ) {
        composable(Graphs.Root.Main.route) { MainNavGraph(rootNavController = rootNavController) }
    }
}

