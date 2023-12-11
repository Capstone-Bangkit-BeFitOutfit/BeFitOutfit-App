package com.bangkit.befitoutfit.data.repository

import android.graphics.Bitmap
import com.bangkit.befitoutfit.data.local.preferences.SessionPreferences
import com.bangkit.befitoutfit.data.remote.ApiService
import com.bangkit.befitoutfit.helper.BitmapExtensions.toJpeg
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class OutfitRepository(
    private val sessionPreferences: SessionPreferences,
    private val apiService: ApiService,
) {
    fun getOutfit() = flow {
        emit(
            value = apiService.getOutfit(
                token = "Bearer ${sessionPreferences.getSession().first().token}",
            ),
        )
    }

    suspend fun addOutfit(
        name: String,
        type: String,
        image: Bitmap?,
        include: Boolean,
    ) = flow {
        image.toJpeg()?.let {
            emit(
                value = apiService.addOutfit(
                    token = "Bearer ${sessionPreferences.getSession().first().token}",
                    name = name.toRequestBody(
                        contentType = "text/plain".toMediaType(),
                    ),
                    type = type.toRequestBody(
                        contentType = "text/plain".toMediaType(),
                    ),
                    image = MultipartBody.Part.createFormData(
                        name = "image",
                        filename = name,
                        body = it.asRequestBody(
                            contentType = "image/jpeg".toMediaType(),
                        ),
                    ),
                    include = include.toString().toRequestBody(
                        contentType = "text/plain".toMediaType(),
                    ),
                ),
            )
        }
    }

    suspend fun updateOutfit(
        id: Int,
        name: String,
        type: String,
        include: Boolean,
    ) = flow {
        emit(
            value = apiService.updateOutfit(
                token = "Bearer ${sessionPreferences.getSession().first().token}",
                id = id,
                name = name.toRequestBody(
                    "text/plain".toMediaType(),
                ),
                type = type.toRequestBody(
                    "text/plain".toMediaType(),
                ),
                include = include.toString().toRequestBody(
                    "text/plain".toMediaType(),
                ),
            ),
        )
    }
}
