package com.example.dailyvita.domain.common.model

data class DescribedTipModel(
    override val id: Int,
    override val name: String,
    val description: String
) :
    BaseTipModel(id, name)
