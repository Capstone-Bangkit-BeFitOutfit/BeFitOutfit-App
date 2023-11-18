package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.model.Info
import com.bangkit.befitoutfit.data.model.Session
import kotlinx.coroutines.flow.flow

class AuthRepository {
    fun login(email: String, password: String) = flow {
        /*TODO: feature login*/
        emit(Session(name = "John Doe", email = email))
    }

    fun register(name: String, email: String, password: String) = flow {
        /*TODO: feature register*/
        emit(Info(code = 200, message = "Success"))
    }
}
