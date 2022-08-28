package com.github.zharovvv.kmemes.ui.navigation.composition.common.stack

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable

interface LazyStackScope {

    fun items(
        count: Int,
        key: ((index: Int) -> Any)?,
        contentType: (index: Int) -> Any?,
        itemContent: @Composable LazyItemScope.(index: Int) -> Unit
    )
}

inline fun <T> LazyStackScope.items(
    items: List<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) = items(
    count = items.size,
    key = if (key != null) { index: Int -> key(items[index]) } else null,
    contentType = { index: Int -> contentType(items[index]) }
) { index ->
    val lazyItemScope = this
    lazyItemScope.itemContent(items[index])
}