package com.bangkit.befitoutfit.data.repository

import android.graphics.Bitmap
import com.bangkit.befitoutfit.data.remote.ApiService
import com.bangkit.befitoutfit.helper.BitmapExtensions.toJpeg
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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
        image: Bitmap?,
        include: Boolean,
    ) = flow {
        image.toJpeg()?.let {
            emit(
                value = apiService.addOutfit(
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
