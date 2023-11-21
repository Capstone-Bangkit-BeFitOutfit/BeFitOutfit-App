package com.bangkit.befitoutfit.ui.screen.myOutfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.Outfits
import com.bangkit.befitoutfit.data.repository.OutfitRepository
import com.bangkit.befitoutfit.helper.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MyOutfitViewModel(private val outfitRepository: OutfitRepository) : ViewModel() {
    var state = MutableStateFlow<State<Outfits>>(State.Idle)
        private set

    fun outfits() = viewModelScope.launch {
        state.value = State.Loading
        outfitRepository.outfits()
            .catch { state.value = State.Error(it.message ?: "Unknown error") }
            .collect { state.value = State.Success(it) }
    }
}
