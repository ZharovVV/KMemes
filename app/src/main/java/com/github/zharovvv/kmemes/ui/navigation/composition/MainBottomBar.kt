package com.github.zharovvv.kmemes.ui.navigation.composition

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.zharovvv.kmemes.ui.navigation.Tab
import com.github.zharovvv.kmemes.ui.navigation.startTab
import com.github.zharovvv.kmemes.ui.navigation.tabs
import com.github.zharovvv.kmemes.ui.theme.ThemedPreview

@Composable
fun MainBottomBar(tabs: List<Tab>, selectedTabRoute: String, onTabClick: (tab: Tab) -> Unit = {}) {
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
                selected = tab.route == selectedTabRoute,
                onClick = { onTabClick.invoke(tab) }
            )
        }
    }
}

@Composable
@Preview
fun MainBottomBarPreview() {
    ThemedPreview {
        MainBottomBar(tabs = tabs, selectedTabRoute = startTab.route)
    }
}