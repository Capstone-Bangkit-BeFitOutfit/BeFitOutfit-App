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
    emailValue: String,
    emailValid: Boolean,
    onEmailValueChange: (String) -> Unit,
    onEmailClick: () -> Unit,
    passwordValue: String,
    passwordValid: Boolean,
    onPasswordValueChange: (String) -> Unit,
    onPasswordClick: () -> Unit,
    isPasswordVisible: Boolean,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    enable: Boolean,
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
            enable = enable,
            value = emailValue,
            isValid = emailValid,
            onValueChange = onEmailValueChange,
            onClick = onEmailClick,
            focusManager = focusManager,
        )

        TextField(
            textFieldType = TextFieldType.Password,
            enable = enable,
            value = passwordValue,
            isValid = passwordValid,
            onValueChange = onPasswordValueChange,
            onClick = onPasswordClick,
            focusManager = focusManager,
            isVisible = isPasswordVisible,
            imeAction = ImeAction.Done,
        )

        OutlinedButton(
            onClick = {
                focusManager.clearFocus()
                onLoginClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            enabled = enable && emailValue.isNotEmpty() && passwordValue.isNotEmpty() && emailValid && passwordValid,
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
                    onRegisterClick()
                },
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
