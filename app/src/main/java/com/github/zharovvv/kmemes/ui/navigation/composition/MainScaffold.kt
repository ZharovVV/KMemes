package com.github.zharovvv.kmemes.ui.navigation.composition

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.plusAssign
import com.github.zharovvv.kmemes.ext.navigation.currentNonDialogDestinationAsState
import com.github.zharovvv.kmemes.ui.navigation.Routes
import com.github.zharovvv.kmemes.ui.navigation.Tab
import com.github.zharovvv.kmemes.ui.navigation.destinationBy
import com.github.zharovvv.kmemes.ui.navigation.dsl.core.Destination
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavHostController,
    tabs: List<Tab>,
    startTab: Tab,
    content: @Composable (PaddingValues) -> Unit
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
//        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        //при скруглении появляются артефакты, заметные на темной теме.
        //Поэтому используем sheetBackgroundColor = Color.Transparent
        // и закругляем уже само содержимое bottomSheet-а.
        sheetBackgroundColor = Color.Transparent,
        sheetContentColor = MaterialTheme.colorScheme.surface
    ) {
        val currentDestination: Destination by navController.currentNonDialogDestinationAsState(
            initial = destinationBy(startTab.route)
        )
        Scaffold(
            topBar = {
                MainTopBar(
                    title = currentDestination.nameResourceId?.let { id -> stringResource(id) },
                    onNavigationIconClick = { navController.popBackStack() },
                    navigationIconVisibilityPredicate = { currentDestination.route != startTab.route },
                    onSettingsButtonClick = { navController.navigate(Routes.Main.Nested.settings) }
                )
            },
            bottomBar = {
                MainBottomBar(
                    tabs = tabs,
                    selectedTabRoute = currentDestination.route,
                    onTabClick = { tab ->
                        if (tab.route != navController.currentDestination?.route) {
                            navController.navigate(tab.route) {
                                popUpTo(startTab.route)
                                launchSingleTop = true
                            }
                        }
                    }
                )
            },
            content = content
        )
    }
}