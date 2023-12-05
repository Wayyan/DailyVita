package com.example.dailyvita.domain.common.useCase

import com.example.dailyvita.domain.CoroutineUseCase
import com.example.dailyvita.domain.DispatcherProvider
import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.domain.common.repository.CommonRepository

class GetAllHealthConcerns constructor(
    private val repository: CommonRepository,
    dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Unit, List<BaseTipModel>>(dispatcherProvider) {
    override suspend fun provide(input: Unit): List<BaseTipModel> {
        return repository.getAllHealthConcerns()
    }
}