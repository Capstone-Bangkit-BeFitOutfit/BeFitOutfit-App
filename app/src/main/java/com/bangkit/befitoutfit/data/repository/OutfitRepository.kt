package com.bangkit.befitoutfit.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.bangkit.befitoutfit.data.remote.ApiService
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class OutfitRepository(private val apiService: ApiService) {
    fun getOutfit() = flow { emit(apiService.getOutfit()) }

    suspend fun addOutfit(name: String, type: String, uri: Uri) = flow {
        /*TODO: feature uri to photo*/
        emit(
            apiService.addOutfit(
                name = name.toRequestBody("text/plain".toMediaType()),
                type = type.toRequestBody("text/plain".toMediaType()),
                photo = createFormData(
                    name = "photo",
                    filename = uri.lastPathSegment,
                    body = uri.toFile().asRequestBody("image/jpeg".toMediaType())
                )
            )
        )
    }
}
