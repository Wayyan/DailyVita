package com.example.dailyvita.feature.uiModels

import androidx.compose.runtime.Immutable
import com.example.dailyvita.base.mvi.UiEvent
import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.domain.common.model.DescribedTipModel

@Immutable
sealed class MainUiEvent : UiEvent {
    data object GetStarted : MainUiEvent()
    data object NextFromHealthConcern : MainUiEvent()
    data object BackFromHealthConcern : MainUiEvent()
    data class ReorderHealthConcernItems(val from: Int, val to: Int) : MainUiEvent()
    data class SelectHealthConcernItem(val item: BaseTipModel) : MainUiEvent()
    data class DeselectHealthConcernItem(val item: BaseTipModel) : MainUiEvent()

    data object NextFromDiets : MainUiEvent()
    data object BackFromDiets : MainUiEvent()
    data object CheckNoneDiet : MainUiEvent()
    data class CheckChangeDiet(val item: DescribedTipModel) : MainUiEvent()

    data object NextFromAllergies : MainUiEvent()
    data object BackFromAllergies : MainUiEvent()
    data class SelectAllergy(val item: BaseTipModel) : MainUiEvent()
    data object DeselectLastAllergy : MainUiEvent()

    data object GetPersonalizedVitamin : MainUiEvent()
    data object BackFromSurveyHabit : MainUiEvent()

    data object DismissAlert : MainUiEvent()
}
