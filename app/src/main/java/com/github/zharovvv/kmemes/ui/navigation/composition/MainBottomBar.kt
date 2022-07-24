package com.github.zharovvv.kmemes.ui.navigation.composition

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.zharovvv.kmemes.ui.navigation.startTab
import com.github.zharovvv.kmemes.ui.navigation.tabs

@Composable
fun MainBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
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
                    label = { Text(stringResource(tab.nameResourceId)) },
                    alwaysShowLabel = false,
                    selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
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