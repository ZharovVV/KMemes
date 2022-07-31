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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.zharovvv.kmemes.ui.theme.ThemedPreview

@Composable
fun MainTopBar(
    title: String? = null,
    onNavigationIconClick: () -> Unit = {},
    navigationIconVisibilityPredicate: () -> Boolean = { true },
    onSettingsButtonClick: () -> Unit = {}
) {
    SmallTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (title != null) {
                    Text(text = title)
                }
                Spacer(modifier = Modifier.weight(1f, true))
                IconButton(onClick = onSettingsButtonClick) {
                    Icon(
                        imageVector = Icons.Filled.Palette,
                        contentDescription = "Кнопка настроек цвета"
                    )
                }
            }
        },
        navigationIcon = {
            if (navigationIconVisibilityPredicate.invoke()) {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Кнопка назад")
                }
            }
        }
    )
}

@Preview
@Composable
fun MainTopBarPreview() {
    ThemedPreview {
        MainTopBar(title = "Любимые")
    }
}