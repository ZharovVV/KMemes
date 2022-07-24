package com.github.zharovvv.kmemes.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Tab(
    val route: String,
    @StringRes val nameResourceId: Int,
    val imageVector: ImageVector,
    val contentDescription: String? = null
) {
    object RandomMemesTab : Tab(
        route = Graphs.Root.Main.Destinations.RandomMemes.route,
        nameResourceId = Graphs.Root.Main.Destinations.RandomMemes.nameResourceId,
        imageVector = Icons.Filled.Shuffle
    )

    object LatestMemesTab : Tab(
        route = Graphs.Root.Main.Destinations.LatestMemes.route,
        nameResourceId = Graphs.Root.Main.Destinations.LatestMemes.nameResourceId,
        imageVector = Icons.Filled.NewReleases
    )

    object HotMemesTab : Tab(
        route = Graphs.Root.Main.Destinations.HotMemes.route,
        nameResourceId = Graphs.Root.Main.Destinations.HotMemes.nameResourceId,
        imageVector = Icons.Filled.Whatshot
    )

    object FavoriteMemesTab : Tab(
        route = Graphs.Root.Main.Destinations.FavoriteMemes.route,
        nameResourceId = Graphs.Root.Main.Destinations.FavoriteMemes.nameResourceId,
        imageVector = Icons.Filled.Favorite
    )
}

val tabs = listOf(Tab.RandomMemesTab, Tab.LatestMemesTab, Tab.HotMemesTab, Tab.FavoriteMemesTab)
val startTab: Tab = Tab.RandomMemesTab
val routeTabsMap: Map<String, Tab> = tabs.associateBy(Tab::route)