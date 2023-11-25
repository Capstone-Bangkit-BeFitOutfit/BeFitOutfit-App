package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.local.preferences.SessionPreferences
import com.bangkit.befitoutfit.data.model.Session

class SessionRepository(
    private val sessionPreferences: SessionPreferences,
) {
    fun getSession() = sessionPreferences.getSession()

    suspend fun setSession(
        session: Session,
    ) = sessionPreferences.setSession(
        session = session,
    )

    suspend fun clearSession() = sessionPreferences.clearSession()
}
