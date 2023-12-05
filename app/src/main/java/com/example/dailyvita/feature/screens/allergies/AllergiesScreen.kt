package com.example.dailyvita.feature.screens.allergies

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.feature.MainViewModel
import com.example.dailyvita.feature.screens.allergies.components.AllergiesSuggestionListItem
import com.example.dailyvita.feature.screens.allergies.components.TransparentTextField
import com.example.dailyvita.feature.screens.healthConcern.components.HealthConcernItem
import com.example.dailyvita.feature.uiModels.MainUiState
import com.example.dailyvita.ui.theme.Dark
import com.example.dailyvita.ui.theme.DeepOrange500
import com.example.dailyvita.ui.theme.Light
import com.example.dailyvita.ui.theme.LightGreen50
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AllergiesScreen() {
    val viewModel: MainViewModel = koinViewModel()

    val state = viewModel.state.collectAsStateWithLifecycle(initialValue = MainUiState())

    val dataList by remember {
        derivedStateOf {
            state.value.dataState?.allergiesData?.let {
                return@derivedStateOf it
            }
            return@derivedStateOf emptyList()
        }
    }

    val selectedList by remember {
        derivedStateOf {
            state.value.dataState?.selectedAllergiesData?.let {
                return@derivedStateOf it
            }
            return@derivedStateOf emptyList()
        }
    }


    var searchString by remember {
        mutableStateOf("")
    }

    val suggestedList by remember {
        derivedStateOf {
            if (searchString.isEmpty()) {
                return@derivedStateOf dataList.filter {
                    selectedList.contains(it).not()
                }
            }

            dataList.filter {
                selectedList.contains(it).not() && it.name.startsWith(searchString, true)
            }
        }
    }

    val suggestString by remember {
        derivedStateOf {
            if (suggestedList.isEmpty() || searchString.isEmpty()) {
                return@derivedStateOf ""
            }

            suggestedList.last().name
        }
    }

    val showSuggestion by remember {
        derivedStateOf {
            suggestedList.isNotEmpty()
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
                    text = "Write any specific allergies or sensitivity towards specific things. (optional)",
                    fontSize = 20.sp,
                    color = Dark
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Light, RoundedCornerShape(10))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(10))
            ) {
                FlowRow(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedList.forEach {
                        HealthConcernItem(data = it, isSelected = true)
                    }

                    TransparentTextField(onValueChange = {
                        searchString = it
                    }, onBackspaceInEmptyValue = {
                        viewModel.deselectLastAllergy()
                    }, value = searchString,
                        suffixValue = suggestString,
                        placeholder = "Type Something",
                        fontSize = 15.sp,
                        padding = 8.dp
                    )
                }
            }

            if (showSuggestion) {
                Column(
                    modifier = Modifier
                        .background(
                            LightGreen50,
                            RoundedCornerShape(10.dp)
                        )
                        .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)

                    ) {
                        items(suggestedList) {
                            AllergiesSuggestionListItem(name = it.name) {
                                viewModel.selectAllergy(it)
                                searchString = ""
                            }
                        }
                    }
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

        }
    }
}