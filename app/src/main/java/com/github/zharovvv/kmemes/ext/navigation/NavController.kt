package com.github.zharovvv.kmemes.ext.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.github.zharovvv.kmemes.ui.navigation.destinationBy
import com.github.zharovvv.kmemes.ui.navigation.dsl.core.Destination
import com.github.zharovvv.kmemes.ui.navigation.dsl.core.Dialog
import com.github.zharovvv.kmemes.ui.navigation.toDestination
import kotlinx.coroutines.flow.map

@Composable
fun NavController.currentNonDialogDestinationAsState(initial: Destination): State<Destination> =
    currentBackStackEntryFlow
        .map { navBackStackEntry: NavBackStackEntry -> navBackStackEntry.destination }
        .map { navDestination -> destinationBy(navDestination.route!!) }
        .map {
            //Нужно для того, чтобы при появлении диалога старый заголовок сохранялся
            if (it is Dialog) {
                previousBackStackEntry!!.destination.toDestination()
            } else {
                it
            }
        }
        .collectAsState(initial = initial)