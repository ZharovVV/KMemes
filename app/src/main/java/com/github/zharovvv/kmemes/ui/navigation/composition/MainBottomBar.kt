package com.github.zharovvv.kmemes.ui.navigation.composition

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.github.zharovvv.kmemes.ui.navigation.isDialog
import com.github.zharovvv.kmemes.ui.navigation.startTab
import com.github.zharovvv.kmemes.ui.navigation.tabs
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun MainBottomBar(navController: NavHostController) {
    val currentDestination by navController.currentBackStackEntryFlow
        .map { navBackStackEntry: NavBackStackEntry -> navBackStackEntry.destination }
        .filter { entry -> !entry.isDialog }
        .collectAsState(null)

    val shouldShowBottomBar: Boolean = tabs.any { tab -> tab.route == currentDestination?.route }
    if (shouldShowBottomBar) {
        NavigationBar {
            tabs.forEach { tab ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = tab.imageVector,
                            contentDescription = tab.contentDescription
                        )
                    },
                    label = { tab.nameResourceId?.let { Text(stringResource(it)) } },
                    alwaysShowLabel = false,
                    selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true, // Можно так
//                    selected = currentDestination?.route == tab.route, // А можно так
                    onClick = {
                        if (tab.route != navController.currentDestination?.route) {
                            navController.navigate(tab.route) {
                                popUpTo(startTab.route)
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    }
}