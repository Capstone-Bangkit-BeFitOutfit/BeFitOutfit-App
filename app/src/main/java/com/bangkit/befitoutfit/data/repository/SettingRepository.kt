package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.local.preferences.SettingPreferences
import com.bangkit.befitoutfit.data.model.SettingRecommend

class SettingRepository(private val settingPreferences: SettingPreferences) {
    fun getSettingRecommend() = settingPreferences.getRecommend()

    suspend fun setSettingRecommend(settingRecommend: SettingRecommend) =
        settingPreferences.setRecommend(settingRecommend = settingRecommend)
}
