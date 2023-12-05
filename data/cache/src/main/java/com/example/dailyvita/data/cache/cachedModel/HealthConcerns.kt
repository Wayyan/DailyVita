package com.example.dailyvita.data.cache.cachedModel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HealthConcerns(
    @Json(name = "data") val data: List<BaseTipData>
)
