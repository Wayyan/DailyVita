package com.example.dailyvita.data.cache.source

import android.content.Context
import com.example.dailyvita.data.cache.cachedModel.Allergies
import com.example.dailyvita.data.cache.cachedModel.Diets
import com.example.dailyvita.data.cache.cachedModel.HealthConcerns
import com.example.dailyvita.data.common.repository.CommonCacheSource
import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.domain.common.model.DescribedTipModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class CommonCacheSourceImpl constructor(private val context: Context) : CommonCacheSource {

    companion object {
        private const val ALLERGIES_FILE = "json/allergies.json"
        private const val DIETS_FILE = "json/diets.json"
        private const val HEALTH_CONCERN_FILE = "json/health_concern.json"
    }

    private val moshi: Moshi by lazy { Moshi.Builder().build() }
    private val allergiesAdapter: JsonAdapter<Allergies> by lazy { moshi.adapter(Allergies::class.java) }
    private val dietsAdapter: JsonAdapter<Diets> by lazy { moshi.adapter(Diets::class.java) }
    private val healthConcernsAdapter: JsonAdapter<HealthConcerns> by lazy {
        moshi.adapter(HealthConcerns::class.java)
    }

    override suspend fun getAllergies(): List<BaseTipModel> {
        val jsonStr = context.assets.open(ALLERGIES_FILE).bufferedReader()
            .use {
                it.readText()
            }

        allergiesAdapter.fromJson(jsonStr)?.let {
            return it.data.map { tip ->
                tip.mapToBaseTipModel()
            }
        }

        return emptyList()
    }

    override suspend fun getAllDiets(): List<DescribedTipModel> {
        val jsonStr = context.assets.open(DIETS_FILE).bufferedReader()
            .use {
                it.readText()
            }

        dietsAdapter.fromJson(jsonStr)?.let {
            return it.data.map { tip ->
                tip.mapToDescribedTipModel()
            }
        }

        return emptyList()
    }

    override suspend fun getAllHealthConcerns(): List<BaseTipModel> {
        val jsonStr = context.assets.open(HEALTH_CONCERN_FILE).bufferedReader()
            .use {
                it.readText()
            }

        healthConcernsAdapter.fromJson(jsonStr)?.let {
            return it.data.map { tip ->
                tip.mapToBaseTipModel()
            }
        }

        return emptyList()
    }
}