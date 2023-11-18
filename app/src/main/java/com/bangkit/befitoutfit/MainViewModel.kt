package com.bangkit.befitoutfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.repository.SessionRepository
import kotlinx.coroutines.launch

class MainViewModel(private val sessionRepository: SessionRepository) : ViewModel() {
    fun getSession() = sessionRepository.getSession()

    fun clearSession() = viewModelScope.launch { sessionRepository.clearSession() }
}
