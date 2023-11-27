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
    valueName: String,
    isValidName: Boolean,
    onValueChangeName: (String) -> Unit,
    onClickName: () -> Unit,
    valueEmail: String,
    isValidEmail: Boolean,
    onValueChangeEmail: (String) -> Unit,
    onClickEmail: () -> Unit,
    onClickUpdate: () -> Unit,
    dismiss: () -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    when (state) {
        is State.Success -> dismiss()
        is State.Error -> {/*TODO: ContentProfile error handling*/
        }

        else -> {}
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        TextField(
            textFieldType = TextFieldType.Name,
            enabled = state is State.Idle,
            value = valueName,
            isValid = isValidName,
            onValueChange = onValueChangeName,
            onClick = onClickName,
            focusManager = focusManager,
        )

        TextField(
            textFieldType = TextFieldType.Email,
            enabled = state is State.Idle,
            value = valueEmail,
            isValid = isValidEmail,
            onValueChange = onValueChangeEmail,
            onClick = onClickEmail,
            focusManager = focusManager,
            imeAction = ImeAction.Done,
        )

        OutlinedButton(
            onClick = onClickUpdate,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                ),
            enabled = (session.name != valueName || session.email != valueEmail) && isValidName && isValidEmail && valueName.isNotEmpty() && valueEmail.isNotEmpty() && state is State.Idle,
        ) {
            Text(
                text = "Update",
            )
        }
    }
}
