package com.github.zharovvv.kmemes.ui.navigation

import androidx.annotation.StringRes
import com.github.zharovvv.kmemes.R

interface Graph : Destination
interface Destination {
    val route: String

    @get:StringRes
    val nameResourceId: Int?
        get() = null
}

object Graphs {

    object Root : Graph {
        override val route: String = "root"

        object Main : Graph {
            override val route: String = "main"

            sealed class Destinations(
                override val route: String,
                @StringRes override val nameResourceId: Int
            ) : Destination {
                object RandomMemes : Destinations(
                    route = "random_memes",
                    nameResourceId = R.string.random_memes_destination_name
                )

                object LatestMemes : Destinations(
                    route = "latest_memes",
                    nameResourceId = R.string.latest_memes_destination_name
                )

                object HotMemes : Destinations(
                    route = "hot_memes",
                    nameResourceId = R.string.hot_memes_destination_name
                )

                object FavoriteMemes : Destinations(
                    route = "favorite_memes",
                    nameResourceId = R.string.favorite_memes_destination_name
                )
            }

            object Nested : Graph {
                override val route: String = "main_nested"

                sealed class Destinations(
                    override val route: String,
                    @StringRes override val nameResourceId: Int
                ) : Destination {
                    object Settings : Destinations(
                        route = "settings",
                        nameResourceId = R.string.settings_destination_name
                    )
                }
            }
        }
    }
}