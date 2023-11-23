package com.bangkit.befitoutfit.ui.screen.myOutfit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bangkit.befitoutfit.data.model.Outfit
import com.bangkit.befitoutfit.data.model.Outfits
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.ui.component.ColumnOutfit

@Composable
fun MyOutfitScreen(
    state: State<Outfits>,
    modifier: Modifier = Modifier,
    getOutfit: () -> Unit = {},
    detailOutfit: (Outfit) -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is State.Idle -> getOutfit()
            is State.Loading -> {}
            is State.Success -> ColumnOutfit(
                outfits = state.data.data,
                modifier = Modifier.align(alignment = if (state.data.data.isEmpty()) Alignment.Center else Alignment.TopCenter),
                onClick = detailOutfit
            )

            is State.Error -> {}
        }
    }
}
