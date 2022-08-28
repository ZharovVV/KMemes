package com.github.zharovvv.kmemes.ui.navigation.composition.common.stack

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.layout.IntervalList
import androidx.compose.foundation.lazy.layout.getDefaultLazyLayoutKey
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.github.zharovvv.kmemes.ui.navigation.composition.common.stack.hook._LazyItemScopeImpl
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun rememberItemProvider(
    state: LazyStackState,
    content: LazyStackScope.() -> Unit
): LazyStackItemProvider {
    val latestContent = rememberUpdatedState(content)

    // mutableState + LaunchedEffect below are used instead of derivedStateOf to ensure that update
    // of derivedState in return expr will only happen after the state value has been changed.
    val nearestItemsRangeState = remember(state) {
        mutableStateOf(
            Snapshot.withoutReadObservation {
                // State read is observed in composition, causing it to recompose 1 additional time.
                calculateNearestItemsRange(state.firstVisibleItemIndex)
            }
        )
    }
    LaunchedEffect(nearestItemsRangeState) {
        snapshotFlow { calculateNearestItemsRange(state.firstVisibleItemIndex) }
            // MutableState's SnapshotMutationPolicy will make sure the provider is only
            // recreated when the state is updated with a new range.
            .collect { nearestItemsRangeState.value = it }
    }

    return remember(nearestItemsRangeState) {
        LazyStackItemProviderImpl(
            derivedStateOf {
                val stackScope = LazyStackScopeImpl().apply(latestContent.value)
                LazyStackItemsSnapshot(
                    stackScope.intervals,
                    nearestItemsRangeState.value
                )
            }
        )
    }
}

@ExperimentalFoundationApi
internal class LazyStackItemsSnapshot(
    private val intervals: IntervalList<LazyStackIntervalContent>,
    nearestItemsRange: IntRange
) {
    val itemsCount get() = intervals.size

    val basePaddingX = 16.dp
    val basePaddingY = 8.dp

    fun getKey(index: Int): Any {
        val interval = intervals[index]
        val localIntervalIndex = index - interval.startIndex
        val key = interval.value.key?.invoke(localIntervalIndex)
        return key ?: getDefaultLazyLayoutKey(index)
    }

    @Composable
    fun Item(scope: LazyItemScope, index: Int) {
        val interval = intervals[index]
        val localIntervalIndex = index - interval.startIndex
        Surface(
            modifier = Modifier
                .layout { measurable, constraints ->
                    val minWidth: Int
                    val maxWidth: Int
                    if (constraints.hasBoundedWidth) {
                        val width =
                            (constraints.maxWidth - (2 * localIntervalIndex * 16.dp.toPx())).roundToInt()
                                .coerceIn(constraints.minWidth, constraints.maxWidth)
                        minWidth = width
                        maxWidth = width
                    } else {
                        minWidth = constraints.minWidth
                        maxWidth = constraints.maxWidth
                    }
                    val minHeight: Int = constraints.minHeight
                    val maxHeight: Int = constraints.maxHeight
                    val placeable = measurable.measure(
                        Constraints(minWidth, maxWidth, minHeight, maxHeight)
                    )
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                },
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 10.dp
        ) {
            interval.value.item.invoke(scope, localIntervalIndex)
        }
    }

    val keyToIndexMap: Map<Any, Int> = generateKeyToIndexMap(nearestItemsRange, intervals)

    fun getContentType(index: Int): Any? {
        val interval = intervals[index]
        val localIntervalIndex = index - interval.startIndex
        return interval.value.type.invoke(localIntervalIndex)
    }
}

@ExperimentalFoundationApi
internal class LazyStackItemProviderImpl(
    private val itemsSnapshot: State<LazyStackItemsSnapshot>
) : LazyStackItemProvider {

    override val itemScope = _LazyItemScopeImpl()

    override val itemCount get() = itemsSnapshot.value.itemsCount

    override fun getKey(index: Int) = itemsSnapshot.value.getKey(index)

    @Composable
    override fun Item(index: Int) {
        itemsSnapshot.value.Item(itemScope, index)
    }

    override val keyToIndexMap: Map<Any, Int> get() = itemsSnapshot.value.keyToIndexMap

    override fun getContentType(index: Int) = itemsSnapshot.value.getContentType(index)
}

/**
 * Traverses the interval [list] in order to create a mapping from the key to the index for all
 * the indexes in the passed [range].
 * The returned map will not contain the values for intervals with no key mapping provided.
 */
@ExperimentalFoundationApi
internal fun generateKeyToIndexMap(
    range: IntRange,
    list: IntervalList<LazyStackIntervalContent>
): Map<Any, Int> {
    val first = range.first
    check(first >= 0)
    val last = minOf(range.last, list.size - 1)
    return if (last < first) {
        emptyMap()
    } else {
        hashMapOf<Any, Int>().also { map ->
            list.forEach(
                fromIndex = first,
                toIndex = last,
            ) {
                if (it.value.key != null) {
                    val keyFactory = requireNotNull(it.value.key)
                    val start = maxOf(first, it.startIndex)
                    val end = minOf(last, it.startIndex + it.size - 1)
                    for (i in start..end) {
                        map[keyFactory(i - it.startIndex)] = i
                    }
                }
            }
        }
    }
}

/**
 * Returns a range of indexes which contains at least [ExtraItemsNearTheSlidingWindow] items near
 * the first visible item. It is optimized to return the same range for small changes in the
 * firstVisibleItem value so we do not regenerate the map on each scroll.
 */
private fun calculateNearestItemsRange(firstVisibleItem: Int): IntRange {
    val slidingWindowStart = VisibleItemsSlidingWindowSize *
            (firstVisibleItem / VisibleItemsSlidingWindowSize)

    val start = maxOf(slidingWindowStart - ExtraItemsNearTheSlidingWindow, 0)
    val end = slidingWindowStart + VisibleItemsSlidingWindowSize + ExtraItemsNearTheSlidingWindow
    return start until end
}

/**
 * We use the idea of sliding window as an optimization, so user can scroll up to this number of
 * items until we have to regenerate the key to index map.
 */
private val VisibleItemsSlidingWindowSize = 30

/**
 * The minimum amount of items near the current first visible item we want to have mapping for.
 */
private val ExtraItemsNearTheSlidingWindow = 100