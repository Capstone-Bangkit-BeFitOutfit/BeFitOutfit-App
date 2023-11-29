package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.helper.TextFieldType

@Composable
fun ContentDetailOutfit(
    state: State<Unit>,
    valueOutfitName: String,
    isValidOutfitName: Boolean,
    onValueChangeOutfitName: (String) -> Unit,
    onClickOutfitName: () -> Unit,
    onClickUpdate: () -> Unit,
    dismiss: () -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    when (state) {
        is State.Success -> dismiss()
        is State.Error -> {/*TODO: ContentDetailOutfit error handling*/
        }

        else -> {}
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            TextField(
                textFieldType = TextFieldType.OutfitName,
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                enabled = state is State.Idle,
                value = valueOutfitName,
                isValid = isValidOutfitName,
                onValueChange = onValueChangeOutfitName,
                onClick = onClickOutfitName,
                focusManager = focusManager,
                imeAction = ImeAction.Done,
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        height = 200.dp,
                    )
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    )
            ) {}
        }

        item {
            Row(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            ) {
                Button(
                    onClick = {
                        /*TODO: feature add image from camera*/
                    },
                    modifier = Modifier.weight(
                        weight = 1f,
                    ),
                    enabled = state is State.Idle,
                ) {
                    Text(
                        text = "Camera",
                    )
                }

                Spacer(
                    modifier = Modifier.padding(
                        all = 8.dp,
                    )
                )

                Button(
                    onClick = {
                        /*TODO: feature add image from gallery*/
                    },
                    modifier = Modifier.weight(
                        weight = 1f,
                    ),
                    enabled = state is State.Idle,
                ) {
                    Text(
                        text = "Gallery",
                    )
                }
            }
        }

        item {
            OutlinedButton(
                onClick = onClickUpdate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                    ),
                enabled = state is State.Idle && valueOutfitName.isNotEmpty() && isValidOutfitName,
            ) {
                Text(
                    text = "Update",
                )
            }
        }
    }
}
