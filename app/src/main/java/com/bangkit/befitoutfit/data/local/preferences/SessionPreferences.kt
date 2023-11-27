package com.bangkit.befitoutfit.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.befitoutfit.data.model.Session
import kotlinx.coroutines.flow.map

class SessionPreferences(private val datastore: DataStore<Preferences>) {
    fun getSession() = datastore.data.map {
        Session(
            id = it[ID] ?: 0,
            name = it[NAME] ?: "",
            email = it[EMAIL] ?: "",
            token = it[TOKEN] ?: "",
        )
    }

    suspend fun setSession(session: Session) {
        datastore.edit {
            it[ID] = session.id
            it[NAME] = session.name
            it[EMAIL] = session.email
            it[TOKEN] = session.token
        }
    }

    suspend fun clearSession() {
        datastore.edit { it.clear() }
    }

    companion object {
        private val ID = intPreferencesKey("id")
        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
        private val TOKEN = stringPreferencesKey("token")
    }
}
