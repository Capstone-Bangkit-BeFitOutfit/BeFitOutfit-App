package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun ContentSettingRecommend(
    listEvent: List<String>,
    event: String,
    onSelected: (String) -> Unit,
    setSettingRecommend: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            Text(
                text = "Event",
                modifier = Modifier.padding(
                    all = 16.dp,
                ),
                style = MaterialTheme.typography.titleMedium,
            )
        }

        items(
            items = listEvent,
            key = {
                it
            },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        height = 56.dp,
                    )
                    .selectable(
                        selected = it == event,
                        role = Role.RadioButton,
                        onClick = {
                            onSelected(it)
                            setSettingRecommend(it)
                        },
                    )
                    .padding(
                        horizontal = 16.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = it == event,
                    onClick = null,
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        start = 16.dp,
                    )
                )
            }
        }
    }
}
