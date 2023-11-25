package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    nameOutfitValue: String,
    nameOutfitValid: Boolean,
    onNameOutfitValueChange: (String) -> Unit,
    onNameOutfitClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onClickDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    when (state) {
        is State.Success -> onClickDismiss()
        is State.Error -> {/*TODO: ContentDetailOutfit error handling*/
        }

        else -> {}
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        TextField(
            textFieldType = TextFieldType.OutfitName,
            enable = state is State.Idle,
            value = nameOutfitValue,
            isValid = nameOutfitValid,
            onValueChange = onNameOutfitValueChange,
            onClick = onNameOutfitClick,
            focusManager = focusManager,
            imeAction = ImeAction.Done,
        )

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

        OutlinedButton(
            onClick = onUpdateClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                ),
            enabled = state is State.Idle && nameOutfitValue.isNotEmpty() && nameOutfitValid,
        ) {
            Text(
                text = "Update",
            )
        }
    }
}
