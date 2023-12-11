package com.bangkit.befitoutfit.data.model

import com.google.gson.annotations.SerializedName

data class Session(
    val id: Int = 0,
    @SerializedName("username") val name: String = "",
    val email: String = "",
    val token: String = "",
)
