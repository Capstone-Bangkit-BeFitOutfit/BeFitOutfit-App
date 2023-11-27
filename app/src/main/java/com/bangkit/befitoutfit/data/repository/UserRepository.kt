package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.local.preferences.SessionPreferences
import com.bangkit.befitoutfit.data.remote.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val sessionPreferences: SessionPreferences,
    private val apiService: ApiService,
) {
    suspend fun updateUser(
        name: String,
        email: String,
    ) = flow {
        emit(
            value = apiService.updateUser(
                id = sessionPreferences.getSession().first().id,
                name = name,
                email = email,
            )
        )
    }
}
