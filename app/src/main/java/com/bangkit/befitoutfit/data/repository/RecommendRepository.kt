package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.local.preferences.SessionPreferences
import com.bangkit.befitoutfit.data.local.preferences.SettingPreferences
import com.bangkit.befitoutfit.data.remote.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RecommendRepository(
    private val sessionPreferences: SessionPreferences,
    private val settingPreferences: SettingPreferences,
    private val apiService: ApiService,
) {
    fun getRecommend() = flow {
        emit(
            value = apiService.getRecommend(
                token = "Bearer ${sessionPreferences.getSession().first().token}",
                email = sessionPreferences.getSession().first().email,
                event = settingPreferences.getRecommend().first().event
            )
        )
    }
}
