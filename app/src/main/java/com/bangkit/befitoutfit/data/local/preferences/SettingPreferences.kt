package com.bangkit.befitoutfit.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.befitoutfit.data.model.SettingRecommend
import kotlinx.coroutines.flow.map

class SettingPreferences(private val datastore: DataStore<Preferences>) {
    fun getRecommend() = datastore.data.map {
        SettingRecommend(event = it[EVENT] ?: "")
    }

    suspend fun setRecommend(settingRecommend: SettingRecommend) {
        datastore.edit { it[EVENT] = settingRecommend.event }
    }

    companion object {
        private val EVENT = stringPreferencesKey("event")
    }
}
