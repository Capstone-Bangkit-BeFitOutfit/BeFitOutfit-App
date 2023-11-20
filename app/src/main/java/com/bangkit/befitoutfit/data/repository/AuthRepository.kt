package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.model.Info
import com.bangkit.befitoutfit.data.remote.ApiService
import kotlinx.coroutines.flow.flow

class AuthRepository(private val apiService: ApiService) {
    fun login(email: String, password: String) =
        flow { emit(apiService.login(email = email, password = password)) }

    fun register(name: String, email: String, password: String) = flow {
        /*TODO: feature register*/
        emit(Info(code = 200, message = "Success"))
    }
}
