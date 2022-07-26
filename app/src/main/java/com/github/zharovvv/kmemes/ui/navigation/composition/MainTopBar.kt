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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.github.zharovvv.kmemes.ui.navigation.Routes
import com.github.zharovvv.kmemes.ui.navigation.destinationBy
import com.github.zharovvv.kmemes.ui.navigation.dsl.core.Destination
import com.github.zharovvv.kmemes.ui.navigation.dsl.core.Dialog
import com.github.zharovvv.kmemes.ui.navigation.startTab
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun MainTopBar(navController: NavHostController) {
    val currentDestination: Destination by navController.currentBackStackEntryFlow
        .map { navBackStackEntry: NavBackStackEntry -> navBackStackEntry.destination }
        .map { navDestination -> destinationBy(navDestination.route!!) }
        .filter { destination -> destination !is Dialog }
        .collectAsState(initial = destinationBy(startTab.route))

    SmallTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                currentDestination.nameResourceId?.let {
                    Text(text = stringResource(it))
                }
                Spacer(modifier = Modifier.weight(1f, true))
                IconButton(onClick = { navController.navigate(Routes.Main.Nested.settings) }) {
                    Icon(imageVector = Icons.Filled.Palette, contentDescription = null)
                }
            }
        },
        navigationIcon = {
            if (currentDestination.route != startTab.route) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                }
            }
        }
    )
}