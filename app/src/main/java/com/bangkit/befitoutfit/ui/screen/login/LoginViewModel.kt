package com.bangkit.befitoutfit.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.Login
import com.bangkit.befitoutfit.data.repository.AuthRepository
import com.bangkit.befitoutfit.data.repository.SessionRepository
import com.bangkit.befitoutfit.helper.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
) : ViewModel() {
    var state = MutableStateFlow<State<Login>>(State.Idle)
        private set

    fun login(
        email: String,
        password: String,
    ) = viewModelScope.launch(
        context = Dispatchers.IO,
    ) {
        authRepository.login(
            email = email,
            password = password,
        ).onStart {
            state.value = State.Loading
        }.catch {
            state.value = State.Error(
                message = it.message ?: "Unknown error",
            )
        }.onCompletion {
            delay(
                timeMillis = 100L,
            )
            state.value = State.Idle
        }.collect {
            sessionRepository.setSession(
                session = it.data,
            )
            state.value = State.Success(
                data = it,
            )
        }
    }
}
