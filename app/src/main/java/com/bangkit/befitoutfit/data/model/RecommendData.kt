package com.bangkit.befitoutfit.data.model

data class RecommendData(
    val top: List<Outfit> = listOf(),
    val bottom: List<Outfit> = listOf(),
    val extra: List<Outfit> = listOf(),
)
