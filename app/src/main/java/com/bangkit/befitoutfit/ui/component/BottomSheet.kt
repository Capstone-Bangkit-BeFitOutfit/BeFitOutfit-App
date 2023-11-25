package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.data.model.Outfit
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.data.model.SettingRecommend
import com.bangkit.befitoutfit.helper.BottomSheetType
import com.bangkit.befitoutfit.helper.InputChecker.emailChecker
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.helper.TextFieldType
import com.bangkit.befitoutfit.ui.screen.addOutfit.AddOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.detailOutfit.DetailOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.profile.ProfileViewModel
import com.bangkit.befitoutfit.ui.screen.settingRecommend.SettingRecommendViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    showBottomSheet: Boolean,
    bottomSheetType: BottomSheetType,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    session: Session = Session(),
    outfit: Outfit = Outfit(),
    onClickDismiss: () -> Unit = {},
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            modifier = modifier,
            sheetState = sheetState,
        ) {
            Text(
                text = bottomSheetType.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 16.dp,
                    ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge,
            )

            Divider(
                modifier = Modifier.padding(
                    bottom = 16.dp,
                ),
            )

            when (bottomSheetType) {
                BottomSheetType.Profile -> {
                    val viewModel: ProfileViewModel = koinViewModel()

                    var nameValue by remember {
                        mutableStateOf(
                            value = session.name,
                        )
                    }
                    var nameValid by remember {
                        mutableStateOf(
                            value = true,
                        )
                    }

                    var emailValue by remember {
                        mutableStateOf(
                            value = session.email,
                        )
                    }
                    var emailValid by remember {
                        mutableStateOf(
                            value = true,
                        )
                    }

                    ContentProfile(
                        session = session,
                        nameValue = nameValue,
                        nameValid = nameValid,
                        onNameValueChange = {
                            nameValid = it.isNotEmpty()
                            nameValue = it
                        },
                        onNameClick = {
                            nameValue = ""
                        },
                        emailValue = emailValue,
                        emailValid = emailValid,
                        onEmailValueChange = {
                            emailValue = it
                            emailValid = it.emailChecker().isEmpty()
                        },
                        onEmailClick = {
                            emailValue = ""
                        },
                        onUpdateClick = {
                            onClickDismiss()
                            viewModel.setSession(
                                Session(
                                    name = nameValue,
                                    email = emailValue,
                                )
                            )
                        },
                    )
                }

                BottomSheetType.DetailOutfit -> {
                    val viewModel: DetailOutfitViewModel = koinViewModel()

                    var nameOutfitValue by remember {
                        mutableStateOf(
                            value = outfit.name,
                        )
                    }
                    var nameOutfitValid by remember {
                        mutableStateOf(
                            value = true,
                        )
                    }

                    ContentDetailOutfit(
                        state = viewModel.state.collectAsState().value,
                        nameOutfitValue = nameOutfitValue,
                        nameOutfitValid = nameOutfitValid,
                        onNameOutfitValueChange = {
                            nameOutfitValid = it.isNotEmpty()
                            nameOutfitValue = it
                        },
                        onNameOutfitClick = {
                            nameOutfitValue = ""
                        },
                        onUpdateClick = {
                            viewModel.updateOutfit(
                                id = outfit.id,
                                name = nameOutfitValue,
                                type = outfit.type,
                                imageUrl = outfit.imageUrl
                            )
                        },
                        onClickDismiss = onClickDismiss,
                    )
                }

                BottomSheetType.AddOutfit -> {
                    val viewModel: AddOutfitViewModel = koinViewModel()

                    val state = viewModel.state.collectAsState().value

                    val enable = state is State.Idle

                    val focusManager = LocalFocusManager.current

                    var nameOutfitValue by remember {
                        mutableStateOf(
                            value = "",
                        )
                    }
                    var nameOutfitValid by remember {
                        mutableStateOf(
                            value = true,
                        )
                    }

                    when (state) {/*TODO: add outfit state management*/
                        is State.Idle -> {}
                        is State.Loading -> {}
                        is State.Success -> {
                            nameOutfitValue = ""
                            nameOutfitValid = true
                            onClickDismiss()
                        }

                        is State.Error -> {}
                    }

                    TextField(
                        textFieldType = TextFieldType.OutfitName,
                        enable = enable,
                        value = nameOutfitValue,
                        isValid = nameOutfitValid,
                        onValueChange = {
                            nameOutfitValid = it.isNotEmpty()
                            nameOutfitValue = it
                        },
                        onClick = { nameOutfitValue = "" },
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
                                bottom = 16.dp,
                            )
                    ) {}

                    Row(
                        modifier = Modifier.padding(
                            bottom = 16.dp,
                        )
                    ) {
                        Button(
                            onClick = {
                                /*TODO: feature add image from camera*/
                            }, modifier = Modifier.weight(
                                weight = 1f,
                            ), enabled = enable
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
                            }, modifier = Modifier.weight(
                                weight = 1f,
                            ), enabled = enable
                        ) {
                            Text(
                                text = "Gallery",
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            viewModel.addOutfit(
                                name = nameOutfitValue,
                                type = outfit.type,
                                imageUrl = outfit.imageUrl
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = enable/*TODO: add another enable logic*/
                    ) {
                        Text(
                            text = "Upload",
                        )
                    }
                }

                BottomSheetType.SettingRecommend -> {
                    val viewModel: SettingRecommendViewModel = koinViewModel()

                    val listEvent = listOf("Casual", "Formal")

                    val (event, onEventSelected) = remember {
                        mutableStateOf(
                            value = when (runBlocking {
                                viewModel.getSettingRecommend().first().event
                            }) {
                                "Casual" -> listEvent[0]
                                "Formal" -> listEvent[1]
                                else -> listEvent[0]
                            },
                        )
                    }

                    Text(
                        text = "Event",
                        modifier = Modifier.padding(
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
                                            onEventSelected(it)
                                            viewModel.setSettingRecommend(
                                                settingRecommend = SettingRecommend(
                                                    event = it,
                                                )
                                            )
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

            Spacer(
                modifier = Modifier.padding(
                    all = 32.dp,
                ),
            )
        }
    }
}
