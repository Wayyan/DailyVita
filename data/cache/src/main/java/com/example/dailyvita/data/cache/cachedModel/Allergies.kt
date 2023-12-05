package com.example.dailyvita.data.cache.cachedModel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Allergies(
    @Json(name = "data") val data: List<BaseTipData>
)
