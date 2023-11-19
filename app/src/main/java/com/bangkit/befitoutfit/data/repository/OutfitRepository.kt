package com.bangkit.befitoutfit.data.repository

import com.bangkit.befitoutfit.data.model.Outfit
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OutfitRepository {
    fun getOutfit(): Flow<List<Outfit>> = flow {
        /*TODO: feature get outfit*/
        emit(listOf())
        delay(1000L)
        emit((1..3).map {
            Outfit(
                name = "Outfit Name #$it",
                type = when (it % 3) {
                    0 -> "Extra"
                    1 -> "Top"
                    else -> "Bottom"
                },
                imageUrl = "https://github.com/material-components/material-components-android/blob/master/catalog/java/io/material/catalog/assets/res/drawable-xxxhdpi/ic_placeholder.png?raw=true"
            )
        })
    }
}
