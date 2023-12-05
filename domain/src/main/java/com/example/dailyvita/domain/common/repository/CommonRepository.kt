package com.example.dailyvita.domain.common.repository

import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.domain.common.model.DescribedTipModel

interface CommonRepository {
    suspend fun getAllAllergies(): List<BaseTipModel>
    suspend fun getAllDiets(): List<DescribedTipModel>
    suspend fun getAllHealthConcerns(): List<BaseTipModel>
}