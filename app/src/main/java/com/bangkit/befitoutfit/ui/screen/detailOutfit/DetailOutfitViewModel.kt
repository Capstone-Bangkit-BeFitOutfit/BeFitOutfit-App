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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailOutfitViewModel(
    private val outfitRepository: OutfitRepository,
) : ViewModel() {
    var state = MutableStateFlow<State<Unit>>(State.Idle)
        private set

    fun updateOutfit(
        id: Int,
        name: String,
        type: String,
        include: Boolean,
    ) = viewModelScope.launch(
        context = Dispatchers.IO,
    ) {
        outfitRepository.updateOutfit(
            id = id,
            name = name,
            type = type,
            include = include,
        ).onStart {
            state.value = State.Loading
        }.catch {
            state.value = State.Error(
                message = it.message ?: "Unknown error",
            )
        }.onCompletion {
            delay(
                timeMillis = 100L,
            )
            state.value = State.Idle
        }.collect {
            state.value = State.Success(
                data = it,
            )
        }
    }

    fun deleteOutfit(
        id: Int,
    ) = viewModelScope.launch(
        context = Dispatchers.IO,
    ) {
        outfitRepository.deleteOutfit(
            id = id,
        ).onStart {
            state.value = State.Loading
        }.catch {
            state.value = State.Error(
                message = it.message ?: "Unknown error",
            )
        }.onCompletion {
            delay(
                timeMillis = 100L,
            )
            state.value = State.Idle
        }.collect {
            state.value = State.Success(
                data = it,
            )
        }
    }
}
