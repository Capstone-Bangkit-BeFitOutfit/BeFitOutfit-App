package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.data.model.Outfit
import com.bangkit.befitoutfit.data.model.RecommendData

@Composable
fun ColumnRecommend(
    recommendData: RecommendData,
    modifier: Modifier = Modifier,
    onClick: (Outfit) -> Unit = {},
    top: List<Outfit> = recommendData.top,
    bottom: List<Outfit> = recommendData.bottom,
    extra: List<Outfit> = recommendData.extra,
    isEmpty: Boolean = (top.size + bottom.size + extra.size) == 0,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
            ),
        contentPadding = PaddingValues(
            top = if (isEmpty) 0.dp else 16.dp,
            bottom = if (isEmpty) 0.dp else 88.dp,
        ),
        verticalArrangement = if (isEmpty) Arrangement.Center else Arrangement.spacedBy(
            space = 16.dp,
        ),
        horizontalAlignment = if (isEmpty) Alignment.CenterHorizontally else Alignment.Start,
    ) {
        if (top.isNotEmpty()) {
            item {
                Text(
                    text = top.first().type,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            items(
                items = top,
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

        if (bottom.isNotEmpty()) {
            item {
                Text(
                    text = bottom.first().type,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            items(
                items = bottom,
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

        if (extra.isNotEmpty()) {
            item {
                Text(
                    text = extra.first().type,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            items(
                items = extra,
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

        if (isEmpty) item {
            Text(
                text = "No outfit recommendation found",
            )
        }
    }
}
