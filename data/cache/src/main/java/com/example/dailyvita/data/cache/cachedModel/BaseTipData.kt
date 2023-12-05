package com.example.dailyvita.data.cache.cachedModel

import com.example.dailyvita.domain.common.model.BaseTipModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseTipData(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
) {
    fun mapToBaseTipModel(): BaseTipModel {
        return BaseTipModel(id, name)
    }
}
