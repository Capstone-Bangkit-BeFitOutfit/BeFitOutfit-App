package com.bangkit.befitoutfit.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.repository.SessionRepository
import com.bangkit.befitoutfit.data.repository.UserRepository
import com.bangkit.befitoutfit.helper.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    var state = MutableStateFlow<State<Unit>>(State.Idle)
        private set

    fun updateProfile(
        name: String,
        email: String,
    ) = viewModelScope.launch(
        context = Dispatchers.IO,
    ) {
        userRepository.updateUser(
            name = name,
            email = email,
        ).onStart {
            state.value = State.Loading
        }.catch {
            state.value = State.Error(
                message = it.message ?: "Unknown error",
            )
        }.onCompletion {
            delay(
                timeMillis = 50L,
            )
            state.value = State.Idle
        }.collect {
            sessionRepository.setSession(
                session = sessionRepository.getSession().first().copy(
                    name = name,
                    email = email,
                ),
            )
            state.value = State.Success(
                data = Unit,
            )
        }
    }
}
