package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.helper.TextFieldType

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun ContentDetailOutfit(
    state: State<Unit>,
    valueOutfitName: String,
    isValidOutfitName: Boolean,
    onValueChangeOutfitName: (String) -> Unit,
    onClickOutfitName: () -> Unit,
    valueOutfitImageUrl: String,
    valueInclude: Boolean,
    onValueChangeInclude: (Boolean) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    valueOutfitType: String,
    onValueChangeOutfitType: (String) -> Unit,
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
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(
                        context = LocalContext.current,
                    ).data(
                        data = valueOutfitImageUrl,
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
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    ),
            ) {
                TextField(
                    value = valueOutfitType,
                    onValueChange = onValueChangeOutfitType,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
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
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        onExpandedChange(false)
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    listOf(
                        "Top",
                        "Bottom",
                        "Extra",
                    ).forEach {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it,
                                )
                            },
                            onClick = {
                                onValueChangeOutfitType(it)
                                onExpandedChange(false)
                            },
                            modifier = Modifier.fillMaxWidth(),
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
                        onValueChange = onValueChangeInclude,
                    )
                    .padding(
                        horizontal = 16.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = valueInclude,
                    onCheckedChange = null,
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
                enabled = state is State.Idle && valueOutfitName.isNotEmpty() && isValidOutfitName,
            ) {
                Text(
                    text = "Update",
                )
            }
        }
    }
}
