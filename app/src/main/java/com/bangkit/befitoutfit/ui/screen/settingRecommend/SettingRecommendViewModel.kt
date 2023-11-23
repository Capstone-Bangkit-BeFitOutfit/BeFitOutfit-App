package com.bangkit.befitoutfit.ui.screen.settingRecommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.SettingRecommend
import com.bangkit.befitoutfit.data.repository.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingRecommendViewModel(private val settingRepository: SettingRepository) : ViewModel() {
    fun getSettingRecommend() = settingRepository.getSettingRecommend()

    fun setSettingRecommend(settingRecommend: SettingRecommend) =
        viewModelScope.launch(context = Dispatchers.IO) {
            settingRepository.setSettingRecommend(settingRecommend = settingRecommend)
        }
}
