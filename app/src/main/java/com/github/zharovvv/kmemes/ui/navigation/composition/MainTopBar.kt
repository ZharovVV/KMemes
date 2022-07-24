package com.github.zharovvv.kmemes.ui.navigation.composition

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.zharovvv.kmemes.ui.navigation.Graphs
import com.github.zharovvv.kmemes.ui.navigation.routeTabsMap
import com.github.zharovvv.kmemes.ui.navigation.startTab

@Composable
fun MainTopBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    SmallTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    //TODO Сейчас поведение некорректное при открытии диалога
                    text = stringResource(
                        currentDestination?.route?.let { routeTabsMap[it] }?.nameResourceId
                            ?: startTab.nameResourceId
                    )
                )
                Spacer(modifier = Modifier.weight(1f, true))
                IconButton(onClick = { navController.navigate(Graphs.Root.Main.Nested.Destinations.Settings.route) }) {
                    Icon(imageVector = Icons.Filled.Palette, contentDescription = null)
                }
            }
        },
        navigationIcon = {
            //TODO Сейчас поведение некорректное при открытии диалога
            if (navController.currentDestination?.route != startTab.route) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                }
            }
        }
    )
}