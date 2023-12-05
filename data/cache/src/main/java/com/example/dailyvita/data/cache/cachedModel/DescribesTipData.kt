package com.example.dailyvita.data.cache.cachedModel

import com.example.dailyvita.domain.common.model.DescribedTipModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DescribesTipData(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "tool_tip") val description: String
) {
    fun mapToDescribedTipModel(): DescribedTipModel {
        return DescribedTipModel(id, name, description)
    }
}
