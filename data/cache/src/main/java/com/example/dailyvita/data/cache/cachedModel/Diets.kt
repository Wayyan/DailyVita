package com.example.dailyvita.data.cache.cachedModel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Diets(
    @Json(name = "data") val data: List<DescribesTipData>
)
