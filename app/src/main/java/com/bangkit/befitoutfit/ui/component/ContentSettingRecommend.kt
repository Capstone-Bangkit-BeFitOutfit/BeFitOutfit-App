package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
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
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Event",
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            ),
            style = MaterialTheme.typography.titleMedium,
        )

        Column(
            modifier = Modifier.selectableGroup(),
        ) {
            listEvent.forEach {
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
}
