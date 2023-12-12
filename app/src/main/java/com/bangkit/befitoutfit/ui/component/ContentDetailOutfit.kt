package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.befitoutfit.R
import com.bangkit.befitoutfit.data.model.Outfit
import com.bangkit.befitoutfit.helper.ListOutfit
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.helper.StringExtensions.errorMessageHandler
import com.bangkit.befitoutfit.helper.TextFieldType

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun ContentDetailOutfit(
    outfit: Outfit,
    state: State<Unit>,
    valueOutfitName: String,
    onValueChangeOutfitName: (String) -> Unit,
    isValidOutfitName: Boolean,
    onClickOutfitName: () -> Unit,
    valueOutfitType: String,
    onValueChangeOutfitType: (String) -> Unit,
    expandedOutfitType: Boolean,
    onExpandedChangeOutfitType: (Boolean) -> Unit,
    valueOutfitEvent: String,
    onValueChangeOutfitEvent: (String) -> Unit,
    expandedOutfitEvent: Boolean,
    onExpandedChangeOutfitEvent: (Boolean) -> Unit,
    valueInclude: Boolean,
    onValueChangeInclude: (Boolean) -> Unit,
    onClickUpdate: () -> Unit,
    onStateResultFeedback: (String) -> Unit,
    dismiss: () -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    when (state) {
        is State.Success -> dismiss()
        is State.Error -> {
            dismiss()
            onStateResultFeedback(state.message.errorMessageHandler())
        }

        else -> {}
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            Card(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                ),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(
                        context = LocalContext.current,
                    ).data(
                        data = outfit.imageUrl,
                    ).crossfade(
                        enable = true,
                    ).build(),
                    contentDescription = "${valueOutfitName}'s outfit image",
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = painterResource(
                        id = R.drawable.image_placeholder,
                    ),
                    error = rememberVectorPainter(
                        image = Icons.Outlined.ErrorOutline,
                    ),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }

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
            ExposedDropdownMenuBox(
                expanded = expandedOutfitType,
                onExpandedChange = if (state is State.Idle) onExpandedChangeOutfitType else { _ -> },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    ),
            ) {
                OutlinedTextField(
                    value = valueOutfitType,
                    onValueChange = onValueChangeOutfitType,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    enabled = state is State.Idle,
                    readOnly = true,
                    label = {
                        Text(
                            text = "Outfit type",
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Select outfit type",
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandedOutfitType,
                        )
                    },
                )

                ExposedDropdownMenu(
                    expanded = expandedOutfitType,
                    onDismissRequest = {
                        onExpandedChangeOutfitType(false)
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    ListOutfit.type.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it,
                                )
                            },
                            onClick = {
                                onValueChangeOutfitType(it)
                                onExpandedChangeOutfitType(false)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = state is State.Idle,
                        )
                    }
                }
            }
        }

        item {
            ExposedDropdownMenuBox(
                expanded = expandedOutfitEvent,
                onExpandedChange = if (state is State.Idle) onExpandedChangeOutfitEvent else { _ -> },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    ),
            ) {
                OutlinedTextField(
                    value = valueOutfitEvent,
                    onValueChange = onValueChangeOutfitEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    enabled = state is State.Idle,
                    readOnly = true,
                    label = {
                        Text(
                            text = "Outfit event",
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Select outfit event",
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandedOutfitEvent,
                        )
                    },
                )

                ExposedDropdownMenu(
                    expanded = expandedOutfitEvent,
                    onDismissRequest = {
                        onExpandedChangeOutfitEvent(false)
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    ListOutfit.event.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it,
                                )
                            },
                            onClick = {
                                onValueChangeOutfitEvent(it)
                                onExpandedChangeOutfitEvent(false)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = state is State.Idle,
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 16.dp,
                    )
                    .height(
                        height = 56.dp,
                    )
                    .toggleable(
                        value = valueInclude,
                        onValueChange = if (state is State.Idle) onValueChangeInclude else { _ -> },
                    )
                    .padding(
                        horizontal = 16.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = valueInclude,
                    onCheckedChange = null,
                    enabled = state is State.Idle,
                )
                Text(
                    text = "Include this outfit in recommendation",
                    modifier = Modifier.padding(
                        start = 16.dp,
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                )
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
                enabled = state is State.Idle && valueOutfitName.isNotEmpty() && isValidOutfitName && valueOutfitType.isNotEmpty() && valueOutfitEvent.isNotEmpty() && (valueOutfitName != outfit.name || valueOutfitType != outfit.type || valueOutfitEvent != outfit.event || valueInclude != outfit.include),
            ) {
                Text(
                    text = "Update",
                )
            }

            Spacer(
                modifier = Modifier.padding(
                    all = 32.dp,
                ),
            )
        }
    }
}
