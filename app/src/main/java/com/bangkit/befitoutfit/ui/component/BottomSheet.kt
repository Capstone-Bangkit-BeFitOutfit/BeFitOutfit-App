package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.data.model.Outfit
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.helper.BottomSheetType
import com.bangkit.befitoutfit.helper.InputChecker.emailChecker
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

                    ContentAddOutfit(
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
                        onAddClick = {
                            viewModel.addOutfit(
                                name = nameOutfitValue,
                                type = outfit.type,
                                imageUrl = outfit.imageUrl
                            )
                        },
                        onClickDismiss = onClickDismiss,
                    )
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

                    ContentSettingRecommend(
                        listEvent = listEvent,
                        event = event,
                        onSelected = onEventSelected,
                        setSettingRecommend = viewModel::setSettingRecommend,
                    )
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
