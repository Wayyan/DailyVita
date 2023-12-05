package com.example.dailyvita.feature.screens.healthConcern

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyvita.composeUtil.dragAndDrop.DragDropLazyColumn
import com.example.dailyvita.feature.MainViewModel
import com.example.dailyvita.feature.screens.healthConcern.components.HealthConcernItem
import com.example.dailyvita.feature.screens.healthConcern.components.HealthConcernListItem
import com.example.dailyvita.feature.uiModels.MainUiState
import com.example.dailyvita.ui.theme.Dark
import com.example.dailyvita.ui.theme.DeepOrange500
import com.example.dailyvita.ui.theme.Light
import com.example.dailyvita.ui.theme.LightGreen200
import com.example.dailyvita.ui.theme.LightGreen50
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HealthConcernScreen() {
    val viewModel: MainViewModel = koinViewModel()

    val state = viewModel.state.collectAsStateWithLifecycle(initialValue = MainUiState())

    val dataList by remember {
        derivedStateOf {
            state.value.dataState?.healthConcernData?.let {
                return@derivedStateOf it
            }
            return@derivedStateOf emptyList()
        }
    }

    val selectedList by remember {
        derivedStateOf {
            state.value.dataState?.selectedHealthConcernData?.let {
                return@derivedStateOf it
            }
            return@derivedStateOf emptyList()
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (main, button) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .constrainAs(main) {
                    top.linkTo(parent.top, 48.dp)
                    bottom.linkTo(button.top)
                    height = Dimension.fillToConstraints
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Select the top health concerns.\n(upto 5)",
                    fontSize = 20.sp,
                    color = Dark
                )
                Text(
                    text = "*",
                    fontSize = 20.sp,
                    color = DeepOrange500
                )
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dataList.forEach { item ->
                    HealthConcernItem(
                        data = item,
                        isSelected = selectedList.contains(item)
                    ) { data, isSelected ->
                        if (isSelected) {
                            viewModel.deselectHealthConcernItem(data)
                        } else {
                            viewModel.selectHealthConcernItem(data)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Prioritize",
                fontSize = 20.sp,
                color = Dark
            )

            Spacer(modifier = Modifier.height(8.dp))

            var draggedItemIndex by remember {
                mutableStateOf<Int?>(null)
            }

            DragDropLazyColumn(
                items = selectedList,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onMove = { from, to ->
                    viewModel.reorderSelectedHealConcernList(from, to)
                },
                onDragging = {
                    draggedItemIndex = it
                },
            ) { index, item ->

                val color = if (draggedItemIndex == index) LightGreen50 else LightGreen200

                HealthConcernListItem(data = item, itemColor = color)
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom, 56.dp)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier
                    .padding(
                        vertical = 4.dp,
                        horizontal = 8.dp
                    )
                    .clickable {
                        viewModel.clickBackFromHealthConcern()
                    },
                text = "Back",
                textAlign = TextAlign.Center,
                color = DeepOrange500,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.width(24.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DeepOrange500,
                    contentColor = DeepOrange500
                ),
                onClick = {
                    viewModel.clickNextFromHealthConcern()
                }) {
                Text(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp,
                            horizontal = 8.dp
                        ),
                    text = "Next",
                    textAlign = TextAlign.Center,
                    color = Light,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}