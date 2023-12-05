package com.bangkit.befitoutfit.ui.screen.addOutfit

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.befitoutfit.data.model.Info
import com.bangkit.befitoutfit.data.repository.OutfitRepository
import com.bangkit.befitoutfit.helper.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AddOutfitViewModel(
    private val outfitRepository: OutfitRepository,
) : ViewModel() {
    var state = MutableStateFlow<State<Info>>(State.Idle)
        private set

    fun addOutfit(
        name: String,
        type: String,
        image: Bitmap?,
        include: Boolean,
    ) = viewModelScope.launch(
        context = Dispatchers.IO,
    ) {
        outfitRepository.addOutfit(
            name = name,
            type = type,
            image = image,
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
}
