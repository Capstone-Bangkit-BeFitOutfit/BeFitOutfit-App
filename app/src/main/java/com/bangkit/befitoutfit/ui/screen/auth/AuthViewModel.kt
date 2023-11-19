package com.bangkit.befitoutfit.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.Info
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.data.repository.AuthRepository
import com.bangkit.befitoutfit.data.repository.SessionRepository
import com.bangkit.befitoutfit.helper.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
) : ViewModel() {
    var stateLogin = MutableStateFlow<State<Session>>(State.Idle)
        private set

    var stateRegister = MutableStateFlow<State<Info>>(State.Idle)
        private set

    fun login(email: String, password: String) = viewModelScope.launch {
        stateLogin.value = State.Loading
        delay(5000L)
        authRepository.login(email, password)
            .catch { stateLogin.value = State.Error(it.message ?: "Unknown error") }.collect {
                sessionRepository.setSession(it)
                stateLogin.value = State.Success(it)
            }
    }

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        stateRegister.value = State.Loading
        delay(5000L)
        authRepository.register(name, email, password)
            .catch { stateRegister.value = State.Error(it.message ?: "Unknown error") }.collect {
                stateRegister.value = State.Success(it)
                delay(1L)
                stateRegister.value = State.Idle
            }
    }
}
