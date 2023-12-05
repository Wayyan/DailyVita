package com.example.dailyvita.feature.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyvita.extension.showShortToast
import com.example.dailyvita.feature.MainViewModel
import com.example.dailyvita.feature.components.LinearProgressBar
import com.example.dailyvita.feature.screens.allergies.AllergiesScreen
import com.example.dailyvita.feature.screens.diet.DietScreen
import com.example.dailyvita.feature.screens.healthConcern.HealthConcernScreen
import com.example.dailyvita.feature.uiModels.MainAlertState
import com.example.dailyvita.feature.uiModels.MainScreenState
import com.example.dailyvita.feature.uiModels.MainUiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainHostScreen() {

    val viewModel: MainViewModel = koinViewModel()

    val context = LocalContext.current

    val state = viewModel.state.collectAsStateWithLifecycle(initialValue = MainUiState())

    val screenState by remember {
        derivedStateOf {
            state.value.uiState
        }
    }

    val toastState by remember {
        derivedStateOf {
            state.value.alertState?.let {
                if (it is MainAlertState.ShowToast) {
                    return@derivedStateOf it.message
                }
            }

            return@derivedStateOf null
        }
    }

    val pagerState = rememberPagerState {
        5
    }

    val progress by remember {
        derivedStateOf {
            pagerState.currentPage / 4.0f
        }
    }

    val coroutineScope = rememberCoroutineScope()

    when (screenState) {
        null, MainScreenState.GetStartedScreen -> {
            coroutineScope.launch {
                pagerState.animateScrollToPage(0)
            }
        }

        MainScreenState.PickHealthConcernsScreen -> {
            coroutineScope.launch {
                pagerState.animateScrollToPage(1)
            }
        }

        MainScreenState.PickDietsScreen -> {
            coroutineScope.launch {
                pagerState.animateScrollToPage(2)
            }
        }

        MainScreenState.PickAllergiesScreen -> {
            coroutineScope.launch {
                pagerState.animateScrollToPage(3)
            }
        }

        MainScreenState.SurveyHabitScreen -> {
            coroutineScope.launch {
                pagerState.animateScrollToPage(4)
            }
        }
    }

    toastState?.let {
        context.showShortToast(it)
        viewModel.dismissAlert()
    }


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (pager, progressBar) = createRefs()

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(pager) {
                    top.linkTo(parent.top)
                    bottom.linkTo(progressBar.top)
                    height = Dimension.fillToConstraints
                },
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> GetStartedScreen()
                1 -> HealthConcernScreen()
                2 -> DietScreen()
                3 -> AllergiesScreen()
                4 -> SurveyHabitScreen()
            }
        }

        LinearProgressBar(
            modifier = Modifier
                .height(4.dp)
                .constrainAs(progressBar) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }, progress = progress
        )


    }


}