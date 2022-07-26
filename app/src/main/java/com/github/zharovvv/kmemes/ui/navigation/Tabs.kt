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
    @StringRes val nameResourceId: Int?,
    val imageVector: ImageVector,
    val contentDescription: String? = null
) {
    object RandomMemesTab : Tab(
        route = Routes.Main.randomMemes,
        nameResourceId = destinationBy(Routes.Main.randomMemes).nameResourceId,
        imageVector = Icons.Filled.Shuffle
    )

    object LatestMemesTab : Tab(
        route = Routes.Main.latestMemes,
        nameResourceId = destinationBy(Routes.Main.latestMemes).nameResourceId,
        imageVector = Icons.Filled.NewReleases
    )

    object HotMemesTab : Tab(
        route = Routes.Main.hotMemes,
        nameResourceId = destinationBy(Routes.Main.hotMemes).nameResourceId,
        imageVector = Icons.Filled.Whatshot
    )

    object FavoriteMemesTab : Tab(
        route = Routes.Main.favoriteMemes,
        nameResourceId = destinationBy(Routes.Main.favoriteMemes).nameResourceId,
        imageVector = Icons.Filled.Favorite
    )
}

val tabs = listOf(Tab.RandomMemesTab, Tab.LatestMemesTab, Tab.HotMemesTab, Tab.FavoriteMemesTab)
val startTab: Tab = Tab.RandomMemesTab
val routeTabsMap: Map<String, Tab> = tabs.associateBy(Tab::route)