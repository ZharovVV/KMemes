package com.github.zharovvv.kmemes.ui.navigation.composition.common.stack

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.gestures.DragScope
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.coroutineScope

@Stable
class LazyStackState(
    firstVisibleItemIndex: Int = 0
) : DraggableState {

    private val onDelta: (Float) -> Unit = {
        //TODO переделать
    }

    private val dragScope: DragScope = object : DragScope {
        override fun dragBy(pixels: Float): Unit = onDelta(pixels)
    }

    private val scrollMutex = MutatorMutex()

    //TODO переделать
    val firstVisibleItemIndex: Int = firstVisibleItemIndex

    override suspend fun drag(
        dragPriority: MutatePriority,
        block: suspend DragScope.() -> Unit
    ): Unit = coroutineScope {
        scrollMutex.mutateWith(dragScope, dragPriority, block)
    }

    override fun dispatchRawDelta(delta: Float) {
        return onDelta(delta)
    }

    companion object {
        val Saver: Saver<LazyStackState, *> = listSaver(
            save = { listOf(it.firstVisibleItemIndex) },
            restore = {
                LazyStackState(
                    firstVisibleItemIndex = it[0]
                )
            }
        )
    }
}

@Composable
fun rememberLazyStackState(
    initialFirstVisibleItemIndex: Int = 0
): LazyStackState {
    return rememberSaveable(saver = LazyStackState.Saver) {
        LazyStackState(initialFirstVisibleItemIndex)
    }
}