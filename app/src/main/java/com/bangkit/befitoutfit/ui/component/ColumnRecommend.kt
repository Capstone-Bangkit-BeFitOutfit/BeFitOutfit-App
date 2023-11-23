package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.data.model.Outfit
import com.bangkit.befitoutfit.data.model.Recommend

@Composable
fun ColumnRecommend(
    recommend: Recommend,
    modifier: Modifier = Modifier,
    onClick: (Outfit) -> Unit = {},
) {
    if (recommend.message != "success") Text(
        text = "No outfit recommendation found", modifier = modifier
    )
    else LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (recommend.data.top.isNotEmpty()) {
            item {
                Text(
                    text = recommend.data.top.first().type,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(items = recommend.data.top, key = { it.toString() }) { outfit ->
                ItemOutfit(outfit = outfit, onClick = { onClick(outfit) })
            }
        }

        if (recommend.data.bottom.isNotEmpty()) {
            item {
                Text(
                    text = recommend.data.bottom.first().type,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(items = recommend.data.bottom, key = { it.toString() }) { outfit ->
                ItemOutfit(outfit = outfit, onClick = { onClick(outfit) })
            }
        }

        if (recommend.data.extra.isNotEmpty()) {
            item {
                Text(
                    text = recommend.data.extra.first().type,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(items = recommend.data.extra, key = { it.toString() }) { outfit ->
                ItemOutfit(outfit = outfit, onClick = { onClick(outfit) })
            }
        }
    }
}
