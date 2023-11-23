package com.bangkit.befitoutfit.ui.screen.detailOutfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.repository.OutfitRepository
import com.bangkit.befitoutfit.helper.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class DetailOutfitViewModel(private val outfitRepository: OutfitRepository) : ViewModel() {
    var state = MutableStateFlow<State<Unit>>(State.Idle)
        private set

    fun updateOutfit(id: Int, name: String, type: String, imageUrl: String) =
        viewModelScope.launch(context = Dispatchers.IO) {
            state.value = State.Loading
            outfitRepository.updateOutfit(id = id, name = name, type = type, imageUrl = imageUrl)
                .catch { state.value = State.Error(message = it.message ?: "Unknown error") }
                .onCompletion {
                    delay(timeMillis = 50L)
                    state.value = State.Idle
                }.collect { state.value = State.Success(data = it) }
        }
}