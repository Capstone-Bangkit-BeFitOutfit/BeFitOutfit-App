package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.data.model.Outfit

@Composable
fun ColumnOutfit(
    outfits: List<Outfit>,
    modifier: Modifier = Modifier,
    onClick: (Outfit) -> Unit = {},
) {
    if (outfits.isEmpty()) Text(text = "No outfit found", modifier = modifier)
    else LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = outfits, key = { it.toString() }) { outfit ->
            ItemOutfit(outfit = outfit, onClick = { onClick(outfit) })
        }
    }
}
