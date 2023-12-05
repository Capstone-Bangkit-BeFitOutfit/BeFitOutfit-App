package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.data.model.Outfit

@Composable
fun ColumnOutfit(
    outfits: List<Outfit>,
    modifier: Modifier = Modifier,
    onClick: (Outfit) -> Unit = {},
    isOutfitsEmpty: Boolean = outfits.isEmpty(),
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
            ),
        contentPadding = PaddingValues(
            top = if (isOutfitsEmpty) 0.dp else 16.dp,
            bottom = if (isOutfitsEmpty) 0.dp else 88.dp,
        ),
        verticalArrangement = if (isOutfitsEmpty) Arrangement.Center else Arrangement.spacedBy(
            space = 16.dp,
        ),
        horizontalAlignment = if (isOutfitsEmpty) Alignment.CenterHorizontally else Alignment.Start,
    ) {
        if (isOutfitsEmpty) item {
            Text(
                text = "No outfit found",
            )
        } else items(
            items = outfits,
            key = {
                it.toString()
            },
        ) { outfit ->
            ItemOutfit(
                outfit = outfit,
            ) {
                onClick(outfit)
            }
        }
    }
}
