package com.example.dailyvita.data.common.repository

import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.domain.common.model.DescribedTipModel

interface CommonCacheSource {
   suspend fun getAllergies(): List<BaseTipModel>
   suspend fun getAllDiets(): List<DescribedTipModel>
   suspend fun getAllHealthConcerns(): List<BaseTipModel>
}