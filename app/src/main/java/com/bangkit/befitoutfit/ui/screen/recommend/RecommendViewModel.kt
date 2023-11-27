package com.bangkit.befitoutfit.ui.screen.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.Recommend
import com.bangkit.befitoutfit.data.repository.RecommendRepository
import com.bangkit.befitoutfit.helper.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RecommendViewModel(
    private val recommendRepository: RecommendRepository,
) : ViewModel() {
    var state = MutableStateFlow<State<Recommend>>(State.Idle)
        private set

    var recommend = Recommend()
        private set

    init {
        getRecommend()
    }

    fun getRecommend() = viewModelScope.launch(
        context = Dispatchers.IO,
    ) {
        recommendRepository.getRecommend().onStart {
            state.value = State.Loading
        }.catch {
            state.value = State.Error(
                message = it.message ?: "Unknown error",
            )
            recommend = Recommend()
        }.onCompletion {
            delay(
                timeMillis = 100L,
            )
            state.value = State.Idle
        }.collect {
            state.value = State.Success(
                data = it,
            )
            recommend = (state.value as State.Success<Recommend>).data
        }
    }
}
