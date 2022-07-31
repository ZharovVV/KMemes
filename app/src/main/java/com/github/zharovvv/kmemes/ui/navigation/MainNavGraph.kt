package com.github.zharovvv.kmemes.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.github.zharovvv.kmemes.di.koinComposeViewModel
import com.github.zharovvv.kmemes.ui.navigation.composition.MainScaffold
import com.github.zharovvv.kmemes.ui.settings.SettingsScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

@Composable
fun MainNavGraph(rootNavController: NavHostController? = null) {
    val navController = rememberNavController()
    MainScaffold(navController = navController, tabs = tabs, startTab = startTab) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startTab.route,
            modifier = Modifier.padding(innerPadding),
            route = Routes.main
        ) {
            composable(Routes.Main.randomMemes) {}
            composable(Routes.Main.latestMemes) {}
            composable(Routes.Main.hotMemes) {}
            composable(Routes.Main.favoriteMemes) {}
            nestedNavGraph()
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
private fun NavGraphBuilder.nestedNavGraph() {
    navigation(
        startDestination = Routes.Main.Nested.settings,
        route = Routes.Main.nested
    ) {
        bottomSheet(Routes.Main.Nested.settings) {
            SettingsScreen(koinComposeViewModel())
        }
    }
}

