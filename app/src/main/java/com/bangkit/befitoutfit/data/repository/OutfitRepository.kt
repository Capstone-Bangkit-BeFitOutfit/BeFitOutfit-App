package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.remote.ApiService
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class OutfitRepository(
    private val apiService: ApiService,
) {
    fun getOutfit() = flow {
        emit(
            value = apiService.getOutfit(),
        )
    }

    suspend fun addOutfit(
        name: String,
        type: String,
        imageUrl: String,
    ) = flow {
        /*TODO: feature uri to photo*/
        emit(
            value = apiService.addOutfit(
                name = name.toRequestBody("text/plain".toMediaType()),
                type = type.toRequestBody("text/plain".toMediaType()),
                imageUrl = imageUrl.toRequestBody("text/plain".toMediaType())
            ),
        )
    }

    suspend fun updateOutfit(
        id: Int,
        name: String,
        type: String,
        imageUrl: String,
    ) = flow {
        /*TODO: feature uri to photo*/
        emit(
            value = apiService.updateOutfit(
                id = id,
                name = name.toRequestBody("text/plain".toMediaType()),
                type = type.toRequestBody("text/plain".toMediaType()),
                imageUrl = imageUrl.toRequestBody("text/plain".toMediaType())
            ),
        )
    }
}
