package com.bangkit.befitoutfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.data.repository.SessionRepository
import kotlinx.coroutines.launch

class MainViewModel(private val sessionRepository: SessionRepository) : ViewModel() {
    fun getSession() = sessionRepository.getSession()

    fun setSession(session: Session) =
        viewModelScope.launch { sessionRepository.setSession(session) }

    fun clearSession() = viewModelScope.launch { sessionRepository.clearSession() }
}
