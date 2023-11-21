package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.remote.ApiService
import kotlinx.coroutines.flow.flow

class OutfitRepository(private val apiService: ApiService) {
    fun getOutfit() = flow { emit(apiService.getOutfit()) }
}
