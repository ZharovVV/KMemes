package com.github.zharovvv.kmemes.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.plusAssign
import com.github.zharovvv.kmemes.di.koinComposeViewModel
import com.github.zharovvv.kmemes.ui.navigation.composition.MainBottomBar
import com.github.zharovvv.kmemes.ui.navigation.composition.MainTopBar
import com.github.zharovvv.kmemes.ui.settings.SettingsScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun MainNavGraph(rootNavController: NavHostController) {
    val navController = rememberNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
//        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        //при скруглении появляются артефакты, заметные на темной теме.
        //Поэтому используем sheetBackgroundColor = Color.Transparent
        // и закругляем уже само содержимое.
        //TODO fix java.lang.IllegalArgumentException: The initial value must have an associated anchor.
        sheetBackgroundColor = Color.Transparent,
        sheetContentColor = MaterialTheme.colorScheme.surface
    ) {
        Scaffold(
            topBar = { MainTopBar(navController) },
            bottomBar = { MainBottomBar(navController) }
        ) { innerPadding ->
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

