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
import com.bangkit.befitoutfit.data.model.Recommend

@Composable
fun ColumnRecommend(
    recommend: Recommend,
    modifier: Modifier = Modifier,
    onClick: (Outfit) -> Unit = {},
    isSuccess: Boolean = recommend.message == "success",
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
            ),
        contentPadding = PaddingValues(
            top = if (isSuccess) 16.dp else 0.dp,
            bottom = if (isSuccess) 88.dp else 0.dp,
        ),
        verticalArrangement = if (isSuccess) Arrangement.spacedBy(
            space = 16.dp,
        ) else Arrangement.Center,
        horizontalAlignment = if (isSuccess) Alignment.Start else Alignment.CenterHorizontally,
    ) {
        if (isSuccess) {
            if (recommend.data.top.isNotEmpty()) {
                item {
                    Text(
                        text = recommend.data.top.first().type,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                items(
                    items = recommend.data.top,
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

            if (recommend.data.bottom.isNotEmpty()) {
                item {
                    Text(
                        text = recommend.data.bottom.first().type,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                items(
                    items = recommend.data.bottom,
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

            if (recommend.data.extra.isNotEmpty()) {
                item {
                    Text(
                        text = recommend.data.extra.first().type,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                items(
                    items = recommend.data.extra,
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
        } else item {
            Text(
                /*TODO: error handling message*/
                text = "No outfit recommendation found",
            )
        }
    }
}
