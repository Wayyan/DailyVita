package com.example.dailyvita.data.common.repository

import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.domain.common.model.DescribedTipModel
import com.example.dailyvita.domain.common.repository.CommonRepository

class CommonRepositoryImpl constructor(private val cacheSource: CommonCacheSource) :
    CommonRepository {

    private var allagies: List<BaseTipModel>? = null
    private var diets: List<DescribedTipModel>? = null
    private var healthConcerns: List<BaseTipModel>? = null

    override suspend fun getAllAllergies(): List<BaseTipModel> {
        allagies?.let { return it }

        return cacheSource.getAllergies().also {
            allagies = it
        }
    }

    override suspend fun getAllDiets(): List<DescribedTipModel> {
        diets?.let { return it }

        return cacheSource.getAllDiets().also {
            diets = it
        }
    }

    override suspend fun getAllHealthConcerns(): List<BaseTipModel> {
        healthConcerns?.let { return it }

        return cacheSource.getAllHealthConcerns().also {
            healthConcerns = it
        }
    }
}