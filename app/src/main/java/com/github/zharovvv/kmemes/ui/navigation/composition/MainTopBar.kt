package com.github.zharovvv.kmemes.ui.navigation.composition

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    Text(text = title, style = MaterialTheme.typography.headlineLarge)
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
    //TODO Пофиксить - сейчас при отсутствии навигацонной иконки все съезжает.
    Column {
        ThemedPreview {
            MainTopBar(title = "Любимые")
        }
        Spacer(modifier = Modifier.height(16.dp))
        ThemedPreview {
            MainTopBar(title = "Случайные", navigationIconVisibilityPredicate = { false })
        }
    }
}