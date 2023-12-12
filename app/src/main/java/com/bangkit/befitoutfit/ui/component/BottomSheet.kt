package com.bangkit.befitoutfit.ui.component

import android.content.Context
import android.graphics.Bitmap
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
import com.bangkit.befitoutfit.helper.StringExtensions.emailChecker
import com.bangkit.befitoutfit.ui.screen.addOutfit.AddOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.detailOutfit.DetailOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.profile.ProfileViewModel
import com.bangkit.befitoutfit.ui.screen.settingRecommend.SettingRecommendViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun BottomSheet(
    context: Context,
    showBottomSheet: Boolean,
    bottomSheetType: BottomSheetType,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    session: Session = Session(),
    outfit: Outfit = Outfit(),
    onStateResultFeedback: (String) -> Unit = {},
    dismiss: () -> Unit = {},
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

            Divider()

            when (bottomSheetType) {
                BottomSheetType.Profile -> {
                    val viewModel: ProfileViewModel = koinViewModel()

                    var valueName by remember {
                        mutableStateOf(
                            value = session.name,
                        )
                    }
                    var isValidName by remember {
                        mutableStateOf(
                            value = true,
                        )
                    }

                    var valueEmail by remember {
                        mutableStateOf(
                            value = session.email,
                        )
                    }
                    var isValidEmail by remember {
                        mutableStateOf(
                            value = true,
                        )
                    }

                    ContentProfile(
                        state = viewModel.state.collectAsState().value,
                        session = session,
                        valueName = valueName,
                        isValidName = isValidName,
                        onValueChangeName = {
                            isValidName = it.isNotEmpty()
                            valueName = it
                        },
                        onClickName = {
                            valueName = ""
                        },
                        valueEmail = valueEmail,
                        isValidEmail = isValidEmail,
                        onValueChangeEmail = {
                            valueEmail = it
                            isValidEmail = it.emailChecker().isEmpty()
                        },
                        onClickEmail = {
                            valueEmail = ""
                        },
                        onClickUpdate = {
                            viewModel.updateProfile(
                                name = valueName,
                                email = valueEmail,
                            )
                        },
                        onStateResultFeedback = onStateResultFeedback,
                        dismiss = dismiss,
                    )
                }

                BottomSheetType.DetailOutfit -> {
                    val viewModel: DetailOutfitViewModel = koinViewModel()

                    var valueOutfitName by remember {
                        mutableStateOf(
                            value = outfit.name,
                        )
                    }
                    var isValidOutfitName by remember {
                        mutableStateOf(
                            value = true,
                        )
                    }

                    var valueInclude by remember {
                        mutableStateOf(
                            value = outfit.include,
                        )
                    }

                    var expanded by remember {
                        mutableStateOf(
                            value = false,
                        )
                    }
                    var valueOutfitType by remember {
                        mutableStateOf(
                            value = outfit.type,
                        )
                    }

                    var expandedOutfitEvent by remember {
                        mutableStateOf(
                            value = false,
                        )
                    }
                    var valueOutfitEvent by remember {
                        mutableStateOf(
                            value = outfit.event,
                        )
                    }

                    ContentDetailOutfit(
                        state = viewModel.state.collectAsState().value,
                        outfit = outfit,
                        valueOutfitName = valueOutfitName,
                        isValidOutfitName = isValidOutfitName,
                        onValueChangeOutfitName = {
                            isValidOutfitName = it.isNotEmpty()
                            valueOutfitName = it
                        },
                        onClickOutfitName = {
                            valueOutfitName = ""
                        },
                        valueOutfitImageUrl = outfit.imageUrl,
                        valueInclude = valueInclude,
                        onValueChangeInclude = {
                            valueInclude = it
                        },
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = it
                        },
                        valueOutfitType = valueOutfitType,
                        onValueChangeOutfitType = {
                            valueOutfitType = it
                        },
                        expandedOutfitEvent = expandedOutfitEvent,
                        onExpandedChangeOutfitEvent = {
                            expandedOutfitEvent = it
                        },
                        valueOutfitEvent = valueOutfitEvent,
                        onValueChangeOutfitEvent = {
                            valueOutfitEvent = it
                        },
                        onClickUpdate = {
                            viewModel.updateOutfit(
                                id = outfit.id,
                                name = valueOutfitName,
                                type = outfit.type,
                                include = valueInclude,
                            )
                        },
                        onStateResultFeedback = onStateResultFeedback,
                        dismiss = dismiss,
                    )
                }

                BottomSheetType.AddOutfit -> {
                    val viewModel: AddOutfitViewModel = koinViewModel()

                    var valueOutfitName by remember {
                        mutableStateOf(
                            value = "",
                        )
                    }
                    var isValidOutfitName by remember {
                        mutableStateOf(
                            value = true,
                        )
                    }

                    var valueOutfitImage: Bitmap? by remember {
                        mutableStateOf(
                            value = null,
                        )
                    }

                    var valueInclude by remember {
                        mutableStateOf(
                            value = true,
                        )
                    }

                    var expandedOutfitType by remember {
                        mutableStateOf(
                            value = false,
                        )
                    }
                    var valueOutfitType by remember {
                        mutableStateOf(
                            value = "",
                        )
                    }

                    var expandedOutfitEvent by remember {
                        mutableStateOf(
                            value = false,
                        )
                    }
                    var valueOutfitEvent by remember {
                        mutableStateOf(
                            value = "",
                        )
                    }

                    ContentAddOutfit(
                        /*TODO: rearrange*/
                        state = viewModel.state.collectAsState().value,
                        context = context,
                        valueOutfitName = valueOutfitName,
                        isValidOutfitName = isValidOutfitName,
                        onValueChangeOutfitName = {
                            isValidOutfitName = it.isNotEmpty()
                            valueOutfitName = it
                        },
                        onClickOutfitName = {
                            valueOutfitName = ""
                        },
                        valueOutfitImage = valueOutfitImage,
                        onValueChangeOutfitImage = {
                            valueOutfitImage = it
                        },
                        valueInclude = valueInclude,
                        onValueChangeInclude = {
                            valueInclude = it
                        },
                        expandedOutfitType = expandedOutfitType,
                        onExpandedChangeOutfitType = {
                            expandedOutfitType = it
                        },
                        valueOutfitType = valueOutfitType,
                        onValueChangeOutfitType = {
                            valueOutfitType = it
                        },
                        expandedOutfitEvent = expandedOutfitEvent,
                        onExpandedChangeOutfitEvent = {
                            expandedOutfitEvent = it
                        },
                        valueOutfitEvent = valueOutfitEvent,
                        onValueChangeOutfitEvent = {
                            valueOutfitEvent = it
                        },
                        onClickAdd = {
                            viewModel.addOutfit(
                                name = valueOutfitName,
                                type = valueOutfitType,
                                image = valueOutfitImage,
                                event = valueOutfitEvent,
                                include = valueInclude,
                            )
                        },
                        onStateResultFeedback = onStateResultFeedback,
                        dismiss = dismiss,
                    )
                }

                BottomSheetType.SettingRecommend -> {
                    val viewModel: SettingRecommendViewModel = koinViewModel()

                    val (event, onEventSelected) = remember {
                        mutableStateOf(
                            value = runBlocking {
                                viewModel.getSettingRecommend().first().event
                            },
                        )
                    }

                    ContentSettingRecommend(
                        listEvent = listOf(
                            "Casual",
                            "Formal",
                        ),
                        event = event,
                        onSelected = onEventSelected,
                        setSettingRecommend = viewModel::setSettingRecommend,
                    )
                }
            }
        }
    }
}
