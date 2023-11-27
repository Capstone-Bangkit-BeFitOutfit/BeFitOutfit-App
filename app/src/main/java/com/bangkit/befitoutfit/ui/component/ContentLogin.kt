package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.helper.TextFieldType

@Composable
fun ContentLogin(
    valueEmail: String,
    isValidEmail: Boolean,
    onValueChangeEmail: (String) -> Unit,
    onClickEmail: () -> Unit,
    valuePassword: String,
    isValidPassword: Boolean,
    onValueChangePassword: (String) -> Unit,
    onClickPassword: () -> Unit,
    isVisiblePassword: Boolean,
    onClickLogin: () -> Unit,
    onClickRegister: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            textFieldType = TextFieldType.Email,
            enabled = enabled,
            value = valueEmail,
            isValid = isValidEmail,
            onValueChange = onValueChangeEmail,
            onClick = onClickEmail,
            focusManager = focusManager,
        )

        TextField(
            textFieldType = TextFieldType.Password,
            enabled = enabled,
            value = valuePassword,
            isValid = isValidPassword,
            onValueChange = onValueChangePassword,
            onClick = onClickPassword,
            focusManager = focusManager,
            isVisible = isVisiblePassword,
            imeAction = ImeAction.Done,
        )

        OutlinedButton(
            onClick = {
                focusManager.clearFocus()
                onClickLogin()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            enabled = enabled && valueEmail.isNotEmpty() && valuePassword.isNotEmpty() && isValidEmail && isValidPassword,
        ) {
            Text(
                text = "Login",
            )
        }

        Row {
            Text(
                text = "Don't have an account? ",
            )
            Text(
                text = "Register",
                modifier = Modifier.clickable {
                    focusManager.clearFocus()
                    onClickRegister()
                },
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
