package com.bangkit.befitoutfit.ui.screen.myOutfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.Outfit
import com.bangkit.befitoutfit.data.model.Outfits
import com.bangkit.befitoutfit.data.repository.OutfitRepository
import com.bangkit.befitoutfit.helper.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MyOutfitViewModel(
    private val outfitRepository: OutfitRepository,
) : ViewModel() {
    var state = MutableStateFlow<State<Outfits>>(State.Idle)
        private set

    var outfits = listOf<Outfit>()
        private set

    init {
        getOutfit()
    }

    fun getOutfit() = viewModelScope.launch(
        context = Dispatchers.IO,
    ) {
        outfitRepository.getOutfit().onStart {
            state.value = State.Loading
        }.catch {
            state.value = State.Error(
                message = it.message ?: "Unknown error",
            )
            outfits = listOf()
        }.onCompletion {
            delay(
                timeMillis = 50L,
            )
            state.value = State.Idle
        }.collect {
            state.value = State.Success(
                data = it,
            )
            outfits = (state.value as State.Success<Outfits>).data.data
        }
    }
}
