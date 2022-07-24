package com.github.zharovvv.kmemes.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.github.zharovvv.kmemes.di.koinComposeViewModel
import com.github.zharovvv.kmemes.ui.navigation.composition.MainBottomBar
import com.github.zharovvv.kmemes.ui.navigation.composition.MainTopBar
import com.github.zharovvv.kmemes.ui.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavGraph(rootNavController: NavHostController) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { MainTopBar(navController) },
        bottomBar = { MainBottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startTab.route,
            modifier = Modifier.padding(innerPadding),
            route = Graphs.Root.Main.route
        ) {
            composable(Graphs.Root.Main.Destinations.RandomMemes.route) {}
            composable(Graphs.Root.Main.Destinations.LatestMemes.route) {}
            composable(Graphs.Root.Main.Destinations.HotMemes.route) {}
            composable(Graphs.Root.Main.Destinations.FavoriteMemes.route) {}
            nestedNavGraph()
        }
    }
}

private fun NavGraphBuilder.nestedNavGraph() {
    navigation(
        startDestination = Graphs.Root.Main.Nested.Destinations.Settings.route,
        route = Graphs.Root.Main.Nested.route
    ) {
        dialog(Graphs.Root.Main.Nested.Destinations.Settings.route) {
            SettingsScreen(koinComposeViewModel())
        }
    }
}

