package com.example.dailyvita.composeUtil.dragAndDrop

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun <T> DragDropLazyColumn(
    items: List<T>,
    onMove: (Int, Int) -> Unit,
    onDragging: (Int?) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemWithIndex: @Composable (index: Int, item: T) -> Unit
) {
    val scope = rememberCoroutineScope()
    var overScrollJob by remember {
        mutableStateOf<Job?>(null)
    }
    val dragAndDropListState = rememberDragDropListState { from, to ->
        onMove(from, to)
    }

    onDragging(dragAndDropListState.currentIndexOfDraggedItem)

    LazyColumn(modifier = modifier
        .pointerInput(Unit) {
            detectDragGesturesAfterLongPress(onDrag = { change, offset ->
                change.consume()
                dragAndDropListState.onDrag(offset)

                if (overScrollJob?.isActive == true)
                    return@detectDragGesturesAfterLongPress

                dragAndDropListState
                    .checkForOverScroll()
                    .takeIf { it != 0f }
                    ?.let {
                        overScrollJob = scope.launch {
                            dragAndDropListState.lazyListState.scrollBy(it)
                        }
                    } ?: kotlin.run { overScrollJob?.cancel() }

            },
                onDragStart = { offset ->
                    dragAndDropListState.onDragStart(offset)
                },
                onDragEnd = { dragAndDropListState.onDrugInterrupted() },
                onDragCancel = { dragAndDropListState.onDrugInterrupted() })
        }
        .fillMaxSize(),
        state = dragAndDropListState.lazyListState,
        contentPadding = contentPadding
    ) {
        itemsIndexed(items) { index, item ->
            Column(
                modifier = Modifier
                    .composed {
                        val offsetOrNull =
                            dragAndDropListState.elementDisplacement.takeIf {
                                index == dragAndDropListState.currentIndexOfDraggedItem
                            }

                        Modifier
                            .graphicsLayer {
                                translationY = offsetOrNull ?: 0f
                            }
                    }
                    .fillMaxWidth()
            ) {
                //content
                itemWithIndex(index, item)
            }
        }
    }
}