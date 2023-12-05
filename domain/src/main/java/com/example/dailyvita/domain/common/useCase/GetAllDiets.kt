package com.example.dailyvita.domain.common.useCase

import com.example.dailyvita.domain.CoroutineUseCase
import com.example.dailyvita.domain.DispatcherProvider
import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.domain.common.model.DescribedTipModel
import com.example.dailyvita.domain.common.repository.CommonRepository

class GetAllDiets constructor(
    private val repository: CommonRepository,
    dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Unit, List<DescribedTipModel>>(dispatcherProvider) {
    override suspend fun provide(input: Unit): List<DescribedTipModel> {
        return repository.getAllDiets()
    }
}