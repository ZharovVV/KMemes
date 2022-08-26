package com.github.zharovvv.kmemes.ui.navigation.composition.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpandableGroup(
    expanded: Boolean,
    expandedContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    collapsedContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        content.invoke()
        AnimatedVisibility(visible = !expanded) {
            collapsedContent.invoke()
        }
        AnimatedVisibility(
            visible = expanded,
//            enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
//            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            expandedContent.invoke()
        }
    }
}