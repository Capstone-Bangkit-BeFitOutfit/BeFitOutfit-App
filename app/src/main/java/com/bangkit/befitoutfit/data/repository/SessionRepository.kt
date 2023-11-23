package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.local.preferences.SessionPreferences
import com.bangkit.befitoutfit.data.model.Session
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SessionRepository(private val sessionPreferences: SessionPreferences) {
    val session = runBlocking { sessionPreferences.getSession().first() }

    suspend fun setSession(session: Session) = sessionPreferences.setSession(session)

    suspend fun clearSession() = sessionPreferences.clearSession()
}
