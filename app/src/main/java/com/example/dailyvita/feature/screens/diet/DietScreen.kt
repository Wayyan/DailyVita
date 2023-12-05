package com.example.dailyvita.feature.screens.diet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyvita.domain.common.model.DescribedTipModel
import com.example.dailyvita.feature.MainViewModel
import com.example.dailyvita.feature.screens.diet.components.DietListItem
import com.example.dailyvita.feature.uiModels.MainUiState
import com.example.dailyvita.ui.theme.Dark
import com.example.dailyvita.ui.theme.DeepOrange500
import com.example.dailyvita.ui.theme.Light
import org.koin.androidx.compose.koinViewModel

@Composable
fun DietScreen() {
    val viewModel: MainViewModel = koinViewModel()

    val state = viewModel.state.collectAsStateWithLifecycle(initialValue = MainUiState())

    val dataList by remember {
        derivedStateOf {
            state.value.dataState?.dietData?.let {
                return@derivedStateOf it
            }
            return@derivedStateOf emptyList()
        }
    }

    val selectedList by remember {
        derivedStateOf {
            state.value.dataState?.selectedDietData?.let {
                return@derivedStateOf it
            }
            return@derivedStateOf emptyList()
        }
    }

    val didSelectNone by remember {
        derivedStateOf {
            state.value.dataState?.selectedDietData.isNullOrEmpty()
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (main, button) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .constrainAs(main) {
                    top.linkTo(parent.top)
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
                    text = "Select the diets you follow.",
                    fontSize = 20.sp,
                    color = Dark
                )
                Text(
                    text = "*",
                    fontSize = 20.sp,
                    color = DeepOrange500
                )
            }

            LazyColumn {
                item {
                    DietListItem(
                        data = DescribedTipModel(id = 0, name = "None", description = ""),
                        hasToolTip = false,
                        isChecked = didSelectNone,
                        onCheckedChange = {
                            viewModel.checkNonDiet()
                        }
                    )
                }

                items(dataList) {
                    DietListItem(
                        data = it,
                        hasToolTip = true,
                        isChecked = selectedList.contains(it),
                        onCheckedChange = {
                            viewModel.checkChangedDiet(it)
                        }
                    )
                }
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
                        viewModel.clickBackFromDiets()
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
                    viewModel.clickNextFromDiets()
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