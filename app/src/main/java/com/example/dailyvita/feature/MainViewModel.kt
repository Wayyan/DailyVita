package com.example.dailyvita.feature

import androidx.lifecycle.viewModelScope
import com.example.dailyvita.base.mvi.BaseViewModel
import com.example.dailyvita.base.mvi.Reducer
import com.example.dailyvita.composeUtil.dragAndDrop.move
import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.domain.common.model.DescribedTipModel
import com.example.dailyvita.domain.common.useCase.GetAllAllergies
import com.example.dailyvita.domain.common.useCase.GetAllDiets
import com.example.dailyvita.domain.common.useCase.GetAllHealthConcerns
import com.example.dailyvita.feature.uiModels.MainAlertState
import com.example.dailyvita.feature.uiModels.MainDataState
import com.example.dailyvita.feature.uiModels.MainScreenState
import com.example.dailyvita.feature.uiModels.MainUiEvent
import com.example.dailyvita.feature.uiModels.MainUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel constructor(
    private val getAllHealthConcerns: GetAllHealthConcerns,
    private val getAllDiets: GetAllDiets,
    private val getAllAllergies: GetAllAllergies
) : BaseViewModel<MainUiState, MainUiEvent>() {

    private val reducer = MainReducer()

    override val state: Flow<MainUiState>
        get() = reducer.state

    private fun sendEvent(event: MainUiEvent) {
        reducer.sendEvent(event)
    }

    fun dismissAlert() {
        sendEvent(MainUiEvent.DismissAlert)
    }

    fun clickGetStarted() {
        sendEvent(MainUiEvent.GetStarted)
    }

    fun clickBackFromHealthConcern() {
        sendEvent(MainUiEvent.BackFromHealthConcern)
    }

    fun clickNextFromHealthConcern() {
        sendEvent(MainUiEvent.NextFromHealthConcern)
    }

    fun reorderSelectedHealConcernList(from: Int, to: Int) {
        sendEvent(MainUiEvent.ReorderHealthConcernItems(from, to))
    }

    fun selectHealthConcernItem(item: BaseTipModel) {
        sendEvent(MainUiEvent.SelectHealthConcernItem(item))
    }

    fun deselectHealthConcernItem(item: BaseTipModel) {
        sendEvent(MainUiEvent.DeselectHealthConcernItem(item))
    }

    fun clickBackFromDiets() {
        sendEvent(MainUiEvent.BackFromDiets)
    }

    fun clickNextFromDiets() {
        sendEvent(MainUiEvent.NextFromDiets)
    }

    fun checkNonDiet() {
        sendEvent(MainUiEvent.CheckNoneDiet)
    }

    fun checkChangedDiet(item: DescribedTipModel) {
        sendEvent(MainUiEvent.CheckChangeDiet(item))
    }

    fun selectAllergy(item: BaseTipModel) {
        sendEvent(MainUiEvent.SelectAllergy(item))
    }

    fun deselectLastAllergy() {
        sendEvent(MainUiEvent.DeselectLastAllergy)
    }

    private inner class MainReducer : Reducer<MainUiState, MainUiEvent>(MainUiState()) {
        override fun reduce(oldState: MainUiState, event: MainUiEvent) {
            when (event) {
                MainUiEvent.GetStarted -> {
                    viewModelScope.launch {
                        val healthConcernData = getAllHealthConcerns.execute(Unit)

                        setState(
                            oldState.copy(
                                uiState = MainScreenState.PickHealthConcernsScreen,
                                dataState = (oldState.dataState ?: MainDataState()).copy(
                                    healthConcernData = healthConcernData
                                )
                            )
                        )
                    }
                }

                MainUiEvent.NextFromHealthConcern -> {
                    viewModelScope.launch {
                        if (oldState.dataState?.selectedHealthConcernData.isNullOrEmpty()) {
                            setState(oldState.copy(alertState = MainAlertState.ShowToast("Please select at least one item")))
                        } else {
                            val dietData = getAllDiets.execute(Unit)
                            setState(
                                oldState.copy(
                                    uiState = MainScreenState.PickDietsScreen,
                                    dataState = (oldState.dataState ?: MainDataState()).copy(
                                        dietData = dietData
                                    )
                                )
                            )
                        }
                    }
                }

                MainUiEvent.BackFromHealthConcern -> {
                    setState(oldState.copy(uiState = MainScreenState.GetStartedScreen))
                }

                is MainUiEvent.ReorderHealthConcernItems -> {
                    val mutableList =
                        oldState.dataState?.selectedHealthConcernData?.toMutableList()!!

                    mutableList.move(event.from, event.to)
                    setState(
                        oldState.copy(
                            dataState = oldState.dataState.copy(
                                selectedHealthConcernData = mutableList.toList()
                            )
                        )
                    )

                }

                is MainUiEvent.SelectHealthConcernItem -> {
                    val mutableList =
                        oldState.dataState?.selectedHealthConcernData?.toMutableList()
                            ?: mutableListOf()
                    if (mutableList.size < 5) {
                        mutableList.add(event.item)
                        setState(
                            oldState.copy(
                                dataState = oldState.dataState?.copy(
                                    selectedHealthConcernData = mutableList.toList()
                                )
                            )
                        )
                    } else {
                        setState(
                            oldState.copy(
                                alertState = MainAlertState.ShowToast("You can only choose 5 items as Max.")
                            )
                        )
                    }
                }

                is MainUiEvent.DeselectHealthConcernItem -> {
                    val mutableList =
                        oldState.dataState?.selectedHealthConcernData?.toMutableList()
                            ?: mutableListOf()
                    mutableList.remove(event.item)
                    setState(
                        oldState.copy(
                            dataState = oldState.dataState?.copy(
                                selectedHealthConcernData = mutableList.toList()
                            )
                        )
                    )
                }

                MainUiEvent.NextFromDiets -> {
                    viewModelScope.launch {
                        val allergiesData = getAllAllergies.execute(Unit)
                        setState(
                            oldState.copy(
                                uiState = MainScreenState.PickAllergiesScreen,
                                dataState = oldState.dataState?.copy(allergiesData = allergiesData)
                            )
                        )
                    }
                }

                MainUiEvent.BackFromDiets -> {
                    setState(
                        oldState.copy(
                            uiState = MainScreenState.PickHealthConcernsScreen
                        )
                    )
                }

                MainUiEvent.CheckNoneDiet -> {
                    setState(
                        oldState.copy(
                            dataState = oldState.dataState?.copy(
                                selectedDietData = emptyList()
                            )
                        )
                    )
                }

                is MainUiEvent.CheckChangeDiet -> {
                    val item = event.item

                    val selectedItemList =
                        (oldState.dataState?.selectedDietData ?: emptyList()).toMutableList()

                    if (selectedItemList.contains(item)) {
                        selectedItemList.remove(item)
                    } else {
                        selectedItemList.add(item)
                    }

                    setState(
                        oldState.copy(
                            dataState = oldState.dataState?.copy(
                                selectedDietData = selectedItemList.toList()
                            )
                        )
                    )
                }

                MainUiEvent.NextFromAllergies -> TODO()
                MainUiEvent.BackFromAllergies -> TODO()

                is MainUiEvent.SelectAllergy -> {
                    val item = event.item
                    val selectedItemList =
                        (oldState.dataState?.selectedAllergiesData ?: emptyList()).toMutableList()

                    selectedItemList.add(item)

                    setState(
                        oldState.copy(
                            dataState = oldState.dataState?.copy(
                                selectedAllergiesData = selectedItemList.toList()
                            )
                        )
                    )
                }

                MainUiEvent.DeselectLastAllergy -> {
                    val selectedItemList =
                        (oldState.dataState?.selectedAllergiesData ?: emptyList()).toMutableList()

                    if (selectedItemList.isNotEmpty()) {
                        selectedItemList.removeLast()
                    }

                    setState(
                        oldState.copy(
                            dataState = oldState.dataState?.copy(
                                selectedAllergiesData = selectedItemList.toList()
                            )
                        )
                    )
                }

                MainUiEvent.BackFromSurveyHabit -> TODO()
                MainUiEvent.GetPersonalizedVitamin -> TODO()

                MainUiEvent.DismissAlert -> {
                    setState(
                        oldState.copy(
                            alertState = null
                        )
                    )
                }
            }
        }

    }
}