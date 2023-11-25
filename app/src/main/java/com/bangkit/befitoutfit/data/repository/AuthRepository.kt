package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.remote.ApiService
import kotlinx.coroutines.flow.flow

class AuthRepository(
    private val apiService: ApiService,
) {
    fun login(
        email: String,
        password: String,
    ) = flow {
        emit(
            value = apiService.login(
                email = email,
                password = password,
            ),
        )
    }

    fun register(
        name: String,
        email: String,
        password: String,
    ) = flow {
        emit(
            value = apiService.register(
                name = name,
                email = email,
                password = password,
            )
        )
    }
}
