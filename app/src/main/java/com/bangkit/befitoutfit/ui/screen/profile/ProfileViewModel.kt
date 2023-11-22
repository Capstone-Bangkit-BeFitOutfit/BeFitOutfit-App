package com.bangkit.befitoutfit.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.data.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val sessionRepository: SessionRepository) : ViewModel() {
    val session = sessionRepository.session

    fun setSession(session: Session) =
        viewModelScope.launch(context = Dispatchers.IO) { sessionRepository.setSession(session) }
}
