package com.github.zharovvv.kmemes.ui.navigation.composition.common.stack

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import com.github.zharovvv.kmemes.ui.navigation.composition.common.stack.hook._LazyItemScopeImpl

@OptIn(ExperimentalFoundationApi::class)
internal interface LazyStackItemProvider : LazyLayoutItemProvider {

    val itemScope: _LazyItemScopeImpl
}