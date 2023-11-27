package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.helper.TextFieldType

@Composable
fun ContentProfile(
    state: State<Unit>,
    session: Session,
    nameValue: String,
    nameValid: Boolean,
    onNameValueChange: (String) -> Unit,
    onNameClick: () -> Unit,
    emailValue: String,
    emailValid: Boolean,
    onEmailValueChange: (String) -> Unit,
    onEmailClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onClickDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    when (state) {
        is State.Success -> onClickDismiss()
        is State.Error -> {/*TODO: ContentProfile error handling*/
        }

        else -> {}
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        TextField(
            textFieldType = TextFieldType.Name,
            enable = state is State.Idle,
            value = nameValue,
            isValid = nameValid,
            onValueChange = onNameValueChange,
            onClick = onNameClick,
            focusManager = focusManager,
        )

        TextField(
            textFieldType = TextFieldType.Email,
            enable = state is State.Idle,
            value = emailValue,
            isValid = emailValid,
            onValueChange = onEmailValueChange,
            onClick = onEmailClick,
            focusManager = focusManager,
            imeAction = ImeAction.Done,
        )

        OutlinedButton(
            onClick = onUpdateClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                ),
            enabled = (session.name != nameValue || session.email != emailValue) && nameValid && emailValid && nameValue.isNotEmpty() && emailValue.isNotEmpty() && state is State.Idle,
        ) {
            Text(
                text = "Update",
            )
        }
    }
}
