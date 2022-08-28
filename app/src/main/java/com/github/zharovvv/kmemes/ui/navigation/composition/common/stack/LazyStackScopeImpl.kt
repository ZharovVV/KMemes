package com.github.zharovvv.kmemes.ui.navigation.composition.common.stack

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.layout.IntervalList
import androidx.compose.foundation.lazy.layout.MutableIntervalList
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
internal class LazyStackScopeImpl : LazyStackScope {

    private val _intervals = MutableIntervalList<LazyStackIntervalContent>()
    val intervals: IntervalList<LazyStackIntervalContent> = _intervals

    override fun items(
        count: Int,
        key: ((index: Int) -> Any)?,
        contentType: (index: Int) -> Any?,
        itemContent: @Composable LazyItemScope.(index: Int) -> Unit
    ) {
        _intervals.addInterval(
            count,
            LazyStackIntervalContent(
                key = key,
                type = contentType,
                item = itemContent
            )
        )
    }
}

internal class LazyStackIntervalContent(
    val key: ((index: Int) -> Any)?,
    val type: ((index: Int) -> Any?),
    val item: @Composable LazyItemScope.(index: Int) -> Unit
)