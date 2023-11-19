package com.bangkit.befitoutfit.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.befitoutfit.data.model.Session
import kotlinx.coroutines.flow.map

class SessionPreferences(private val datastore: DataStore<Preferences>) {
    fun getSession() =
        datastore.data.map { Session(name = it[NAME] ?: "", email = it[EMAIL] ?: "") }

    suspend fun setSession(session: Session) {
        datastore.edit {
            it[NAME] = session.name
            it[EMAIL] = session.email
        }
    }

    suspend fun clearSession() {
        datastore.edit { it.clear() }
    }

    companion object {
        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
    }
}
