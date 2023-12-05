package com.example.dailyvita.feature.uiModels

import androidx.compose.runtime.Immutable
import com.example.dailyvita.base.mvi.UiState
import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.domain.common.model.DescribedTipModel

@Immutable
data class MainUiState(
    val uiState: MainScreenState? = null,
    val dataState: MainDataState? = null,
    val alertState: MainAlertState? = null
) : UiState

sealed class MainScreenState {
    data object GetStartedScreen : MainScreenState()
    data object PickHealthConcernsScreen : MainScreenState()
    data object PickDietsScreen : MainScreenState()
    data object PickAllergiesScreen : MainScreenState()
    data object SurveyHabitScreen : MainScreenState()
}

data class MainDataState(
    val healthConcernData: List<BaseTipModel>? = null,
    val selectedHealthConcernData: List<BaseTipModel> = emptyList(),
    val dietData: List<DescribedTipModel>? = null,
    val selectedDietData: List<DescribedTipModel> = emptyList(),
    val allergiesData: List<BaseTipModel>? = null,
    val selectedAllergiesData: List<BaseTipModel> = emptyList()
)

sealed class MainAlertState {
    data class ShowToast(val message: String) : MainAlertState()
}