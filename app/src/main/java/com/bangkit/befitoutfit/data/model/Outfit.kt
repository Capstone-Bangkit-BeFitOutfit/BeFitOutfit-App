package com.bangkit.befitoutfit.data.model

data class Outfit(
    val id: Int = 0,
    val imageUrl: String = "",
    val name: String = "",
    val type: String = "",
    val event: String = "",
    val include: Boolean = false,
)
