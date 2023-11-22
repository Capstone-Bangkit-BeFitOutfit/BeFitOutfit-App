package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.data.model.Outfit
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.helper.BottomSheetType
import com.bangkit.befitoutfit.helper.InputChecker.emailChecker
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.helper.TextFieldType
import com.bangkit.befitoutfit.ui.screen.addOutfit.AddOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.profile.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    showBottomSheet: Boolean,
    bottomSheetType: BottomSheetType,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    outfit: Outfit = Outfit(name = "", type = "", imageUrl = ""),
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
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )

            Divider(modifier = Modifier.padding(bottom = 16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                when (bottomSheetType) {
                    BottomSheetType.Profile -> {
                        val viewModel: ProfileViewModel = koinViewModel()

                        val session = viewModel.session

                        val focusManager = LocalFocusManager.current

                        var nameValue by remember { mutableStateOf(session.name) }
                        var nameValid by remember { mutableStateOf(true) }

                        var emailValue by remember { mutableStateOf(session.email) }
                        var emailValid by remember { mutableStateOf(true) }

                        TextField(
                            textFieldType = TextFieldType.Name,
                            value = nameValue,
                            isValid = nameValid,
                            onValueChange = {
                                nameValid = it.isNotEmpty()
                                nameValue = it
                            },
                            onClick = { nameValue = "" },
                            focusManager = focusManager,
                        )

                        TextField(
                            textFieldType = TextFieldType.Email,
                            value = emailValue,
                            isValid = emailValid,
                            onValueChange = {
                                emailValue = it
                                emailValid = it.emailChecker().isEmpty()
                            },
                            onClick = { emailValue = "" },
                            focusManager = focusManager,
                            imeAction = ImeAction.Done
                        )

                        OutlinedButton(
                            onClick = {
                                focusManager.clearFocus()
                                viewModel.setSession(Session(name = nameValue, email = emailValue))
                                onClickDismiss()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = (session.name != nameValue || session.email != emailValue) && nameValid && emailValid && nameValue.isNotEmpty() && emailValue.isNotEmpty()
                        ) { Text(text = "Update") }
                    }

                    BottomSheetType.DetailOutfit -> {
                        val focusManager = LocalFocusManager.current

                        var nameOutfitValue by remember { mutableStateOf(outfit.name) }
                        var nameOutfitValid by remember { mutableStateOf(true) }

                        TextField(
                            textFieldType = TextFieldType.OutfitName,
                            value = nameOutfitValue,
                            isValid = nameOutfitValid,
                            onValueChange = {
                                nameOutfitValid = it.isNotEmpty()
                                nameOutfitValue = it
                            },
                            onClick = { nameOutfitValue = "" },
                            focusManager = focusManager,
                            imeAction = ImeAction.Done
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 16.dp)
                        ) {}

                        Row(modifier = Modifier.padding(bottom = 16.dp)) {
                            Button(
                                onClick = { /*TODO: feature add image from camera*/ },
                                modifier = Modifier.weight(1f)
                            ) { Text(text = "Camera") }

                            Spacer(modifier = Modifier.padding(8.dp))

                            Button(
                                onClick = { /*TODO: feature add image from gallery*/ },
                                modifier = Modifier.weight(1f)
                            ) { Text(text = "Gallery") }
                        }

                        OutlinedButton(
                            onClick = {
                                /*TODO: feature update outfit*/
                                onClickDismiss()
                            }, modifier = Modifier.fillMaxWidth(), enabled = true
                        ) { Text(text = "Update") }
                    }

                    BottomSheetType.AddOutfit -> {
                        val viewModel: AddOutfitViewModel = koinViewModel()

                        val state = viewModel.state.collectAsState().value

                        val enable = state is State.Idle

                        val focusManager = LocalFocusManager.current

                        var nameOutfitValue by remember { mutableStateOf("") }
                        var nameOutfitValid by remember { mutableStateOf(true) }

                        when (state) {
                            /*TODO: add outfit state management*/
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
                            imeAction = ImeAction.Done
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 16.dp)
                        ) {}

                        Row(modifier = Modifier.padding(bottom = 16.dp)) {
                            Button(
                                onClick = { /*TODO: feature add image from camera*/ },
                                modifier = Modifier.weight(1f),
                                enabled = enable
                            ) { Text(text = "Camera") }

                            Spacer(modifier = Modifier.padding(8.dp))

                            Button(
                                onClick = { /*TODO: feature add image from gallery*/ },
                                modifier = Modifier.weight(1f),
                                enabled = enable
                            ) { Text(text = "Gallery") }
                        }

                        OutlinedButton(
                            onClick = {
                                viewModel.addOutfit(
                                    name = nameOutfitValue,
                                    type = outfit.type,
                                    imageUrl = outfit.imageUrl
                                )
                            }, modifier = Modifier.fillMaxWidth(), enabled = enable
                        ) { Text(text = "Upload") }
                    }

                    BottomSheetType.SettingRecommend -> {
                        OutlinedButton(
                            onClick = {
                                /*TODO: feature recommend setting*/
                                onClickDismiss()
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) { Text(text = "Save") }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(32.dp))
        }
    }
}
