package com.github.zharovvv.kmemes.ui.navigation

import androidx.navigation.NavDestination
import com.github.zharovvv.kmemes.R
import com.github.zharovvv.kmemes.ui.navigation.dsl.core.*

object Routes {
    const val root = "root"
    const val main = "main"

    object Main {
        const val randomMemes = "random_memes"
        const val latestMemes = "latest_memes"
        const val hotMemes = "hot_memes"
        const val favoriteMemes = "favorite_memes"

        const val nested = "main_nested"

        object Nested {
            const val settings = "settings"
        }
    }
}

val appNavGraph = createGraph(Routes.root) {
    graph(Routes.main) {
        destination(Routes.Main.randomMemes, R.string.random_memes_destination_name)
        destination(Routes.Main.latestMemes, R.string.latest_memes_destination_name)
        destination(Routes.Main.hotMemes, R.string.hot_memes_destination_name)
        destination(Routes.Main.favoriteMemes, R.string.favorite_memes_destination_name)
        graph(Routes.Main.nested) {
            dialog(Routes.Main.Nested.settings, R.string.settings_destination_name)
        }
    }
}

private val routeDestinationMap: Map<String, Destination> =
    appNavGraph.associateBy(Destination::route)

fun NavDestination.toDestination(): Destination =
    routeDestinationMap.getValue(
        route ?: throw IllegalArgumentException("$this does not have route!")
    )

val NavDestination.isDialog: Boolean get() = toDestination() is Dialog

fun destinationBy(route: String): Destination = routeDestinationMap.getValue(route)