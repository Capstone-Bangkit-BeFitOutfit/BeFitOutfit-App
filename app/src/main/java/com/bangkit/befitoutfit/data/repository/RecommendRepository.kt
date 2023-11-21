package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.local.preferences.SessionPreferences
import com.bangkit.befitoutfit.data.remote.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RecommendRepository(
    private val sessionPreferences: SessionPreferences,
    private val apiService: ApiService,
) {
    fun recommend() = flow {
        /*TODO: get event from shared preferences*/
        emit(
            apiService.recommend(
                email = sessionPreferences.getSession().first().email, event = "casual"
            )
        )
    }
}
