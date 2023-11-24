package com.bangkit.befitoutfit.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.Info
import com.bangkit.befitoutfit.data.repository.AuthRepository
import com.bangkit.befitoutfit.helper.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    var state = MutableStateFlow<State<Info>>(State.Idle)
        private set

    fun register(
        name: String,
        email: String,
        password: String,
    ) = viewModelScope.launch(
        context = Dispatchers.IO,
    ) {
        authRepository.register(
            name = name,
            email = email,
            password = password,
        ).onStart {
            state.value = State.Loading
        }.catch {
            state.value = State.Error(
                message = it.message ?: "Unknown error"
            )
        }.onCompletion {
            delay(
                timeMillis = 50L,
            )
            state.value = State.Idle
        }.collect {
            state.value = State.Success(
                data = it,
            )
        }
    }
}
