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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.helper.BottomSheetType
import com.bangkit.befitoutfit.helper.InputChecker.emailChecker
import com.bangkit.befitoutfit.helper.TextFieldType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    showBottomSheet: Boolean,
    bottomSheetType: BottomSheetType,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    session: Session = Session(email = "", name = ""),
    onClickDismiss: () -> Unit = {},
    onClickProfile: (Session) -> Unit = {},
    onClickUpdateOutfit: () -> Unit = {},
    onClickAddOutfit: () -> Unit = {},
    onClickSettingRecommend: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

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
                            focusManager = focusManager
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
                            focusManager = focusManager
                        )

                        OutlinedButton(
                            onClick = {
                                focusManager.clearFocus()
                                onClickProfile(Session(name = nameValue, email = emailValue))
                                onClickDismiss()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = (session.name != nameValue || session.email != emailValue) && nameValid && emailValid && nameValue.isNotEmpty() && emailValue.isNotEmpty()
                        ) { Text(text = "Update") }
                    }

                    BottomSheetType.DetailOutfit -> {
                        TextField(textFieldType = TextFieldType.OutfitName)

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
                                /*TODO: feature add outfit*/
                                onClickUpdateOutfit()
                                onClickDismiss()
                            }, modifier = Modifier.fillMaxWidth(), enabled = true
                        ) { Text(text = "Update") }
                    }

                    BottomSheetType.AddOutfit -> {
                        TextField(textFieldType = TextFieldType.OutfitName)

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
                                /*TODO: feature add outfit*/
                                onClickAddOutfit()
                                onClickDismiss()
                            }, modifier = Modifier.fillMaxWidth(), enabled = true
                        ) { Text(text = "Upload") }
                    }

                    BottomSheetType.SettingRecommend -> {/*TODO: feature recommend setting*/
                        OutlinedButton(
                            onClick = {
                                onClickSettingRecommend()
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
