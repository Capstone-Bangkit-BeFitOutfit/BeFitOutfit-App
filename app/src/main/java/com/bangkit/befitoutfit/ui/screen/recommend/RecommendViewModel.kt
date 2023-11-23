package com.bangkit.befitoutfit.ui.screen.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.Recommend
import com.bangkit.befitoutfit.data.repository.RecommendRepository
import com.bangkit.befitoutfit.helper.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RecommendViewModel(private val recommendRepository: RecommendRepository) : ViewModel() {
    var state = MutableStateFlow<State<Recommend>>(State.Idle)
        private set

    fun getRecommend() = viewModelScope.launch {
        state.value = State.Loading
        recommendRepository.getRecommend()
            .catch { state.value = State.Error(it.message ?: "Unknown error") }
            .collect { state.value = State.Success(it) }
    }
}
