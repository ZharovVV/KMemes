package com.github.zharovvv.kmemes.ui.navigation.composition.common.stack

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScope
import androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyStack(
    modifier: Modifier = Modifier,
    state: LazyStackState = rememberLazyStackState(),
    maxVisibleCount: Int = 3,
    content: LazyStackScope.() -> Unit
) {
    val itemProvider = rememberItemProvider(state, content)
    LazyLayout(
        itemProvider = itemProvider,
        modifier = modifier,
        prefetchState = LazyLayoutPrefetchState(),
        measurePolicy = { constraints: Constraints ->
            val measureScope: LazyLayoutMeasureScope = this
            val baseXPadding = 16.dp.toPx().toInt()
            val baseYPadding = 8.dp.toPx().toInt()
            var x: Int = 0
            var y: Int = maxVisibleCount * baseYPadding
            var zIndex: Float = maxVisibleCount.toFloat()
            val placeableItems = IntRange(
                state.firstVisibleItemIndex,
                min(maxVisibleCount - 1, itemProvider.itemCount - 1)
            ).map { index ->
                val placeables = measureScope.measure(index, constraints)
                StackPlaceableItem(
                    placeables,
                    x, y, zIndex
                ).also {
                    x += baseXPadding
                    y -= baseYPadding
                    zIndex -= 1
                }
            }

            layout(
                width = constraints.maxWidth,
                height = constraints.maxWidth,
                alignmentLines = mapOf(),
                placementBlock = {
                    placeableItems.forEach { stackPlaceableItem ->
                        stackPlaceableItem.placeables[0]
                            .placeRelative(
                                stackPlaceableItem.x,
                                stackPlaceableItem.y,
                                stackPlaceableItem.zIndex
                            )
                    }
                }
            )
        }
    )
}

internal data class StackPlaceableItem(
    val placeables: Array<Placeable>,
    val x: Int,
    val y: Int,
    val zIndex: Float
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StackPlaceableItem

        if (!placeables.contentEquals(other.placeables)) return false
        if (x != other.x) return false
        if (y != other.y) return false
        if (zIndex != other.zIndex) return false

        return true
    }

    override fun hashCode(): Int {
        var result = placeables.contentHashCode()
        result = 31 * result + x
        result = 31 * result + y
        result = 31 * result + zIndex.hashCode()
        return result
    }
}

@Preview
@Composable
fun LazyStackPreview() {
    val contentList = remember {
        listOf("first", "second", "third", "fourth", "fifth")
    }
    LazyStack(Modifier.padding(16.dp)) {
        items(contentList) { item: String ->
            Box(
                modifier = Modifier.size(width = 200.dp, height = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item)
            }
        }
    }
}

