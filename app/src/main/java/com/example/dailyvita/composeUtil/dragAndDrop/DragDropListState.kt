package com.example.dailyvita.composeUtil.dragAndDrop

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.Job

@Composable
fun rememberDragDropListState(
    lazyListState: LazyListState = rememberLazyListState(),
    onMove: (Int, Int) -> Unit
): DragDropListState {
    return remember {
        DragDropListState(lazyListState, onMove)
    }
}

class DragDropListState constructor(
    val lazyListState: LazyListState,
    private val onMove: (Int, Int) -> Unit
) {
    var draggedDistance by mutableStateOf(0f)
    var initiallyDraggedElement by mutableStateOf<LazyListItemInfo?>(null)
    var currentIndexOfDraggedItem by mutableStateOf<Int?>(null)
    val initialOffset: Pair<Int, Int>?
        get() = initiallyDraggedElement?.let {
            Pair(it.offset, it.offsetEnd)
        }

    val elementDisplacement: Float?
        get() = currentIndexOfDraggedItem?.let {
            lazyListState.getVisibleItemInfoFor(it)
        }?.let { item ->
            (initiallyDraggedElement?.offset ?: 0f).toFloat() + draggedDistance - item.offset
        }

    val currentElement: LazyListItemInfo?
        get() = currentIndexOfDraggedItem?.let {
            lazyListState.getVisibleItemInfoFor(it)
        }

    var overScrollJob by mutableStateOf<Job?>(null)

    fun onDragStart(offset: Offset) {
        lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { item ->
            offset.y.toInt() in item.offset..(item.offsetEnd)
        }?.also {
            currentIndexOfDraggedItem = it.index
            initiallyDraggedElement = it
        }
    }

    fun onDrugInterrupted() {
        draggedDistance = 0f
        currentIndexOfDraggedItem = null
        initiallyDraggedElement = null
        overScrollJob?.cancel()
    }

    fun onDrag(offset: Offset) {
        draggedDistance += offset.y

        initialOffset?.let { (topOffset, bottomOffset) ->
            val startOffset = topOffset + draggedDistance
            val endOffset = bottomOffset + draggedDistance

            currentElement?.let { hovered ->
                lazyListState.layoutInfo.visibleItemsInfo
                    .filterNot { item ->
                        item.offsetEnd < startOffset || item.offset > endOffset || hovered.index == item.index
                    }
                    .firstOrNull { item ->
                        val delta = startOffset - hovered.offset
                        when {
                            delta > 0 -> (endOffset > item.offsetEnd)
                            else -> (startOffset < item.offset)
                        }
                    }?.also {
                        currentIndexOfDraggedItem?.let { current ->
                            onMove.invoke(current, it.index)
                        }
                        currentIndexOfDraggedItem = it.index
                    }

            }

        }
    }

    fun checkForOverScroll(): Float {
        return initiallyDraggedElement?.let {
            val startOffset = it.offset + draggedDistance
            val endOffset = it.offsetEnd + draggedDistance

            return@let when {
                draggedDistance > 0 -> (endOffset-lazyListState.layoutInfo.viewportEndOffset).takeIf { diff ->
                    diff > 0
                }

                draggedDistance < 0 -> (startOffset - lazyListState.layoutInfo.viewportStartOffset).takeIf { diff ->
                    diff<0
                }

                else -> null
            }
        } ?: 0f
    }
}