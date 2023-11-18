package com.bangkit.befitoutfit.ui.screen.auth

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.data.model.Info
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.helper.InputChecker.emailChecker
import com.bangkit.befitoutfit.helper.InputChecker.passwordChecker
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.helper.TextFieldType
import com.bangkit.befitoutfit.ui.component.TextField

@Composable
fun AuthScreen(
    stateLogin: State<Session>,
    stateRegister: State<Info>,
    modifier: Modifier = Modifier,
    onClickLogin: (String, String) -> Unit = { _, _ -> },
    onClickRegister: (String, String, String) -> Unit = { _, _, _ -> },
    navigateToMain: () -> Unit = {},
) {
    var isLogin by remember { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current

    val isLoading = stateLogin is State.Loading || stateRegister is State.Loading

    var nameValue by remember { mutableStateOf("") }
    var nameValid by remember { mutableStateOf(true) }

    var emailValue by remember { mutableStateOf("") }
    var emailValid by remember { mutableStateOf(true) }

    var passwordValue by remember { mutableStateOf("") }
    var passwordValid by remember { mutableStateOf(true) }
    var passwordVisible by remember { mutableStateOf(false) }

    if (stateLogin is State.Success) navigateToMain()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = !isLogin) {
            TextField(
                textFieldType = TextFieldType.Name,
                modifier = Modifier.fillMaxWidth(),
                enable = !isLoading,
                value = nameValue,
                isValid = nameValid,
                onValueChange = {
                    nameValid = it.isNotEmpty()
                    nameValue = it
                },
                onClick = { nameValue = "" },
                focusManager = focusManager,
            )
        }

        TextField(
            textFieldType = TextFieldType.Email,
            modifier = Modifier.fillMaxWidth(),
            enable = !isLoading,
            value = emailValue,
            isValid = emailValid,
            onValueChange = {
                emailValue = it
                emailValid = emailValue.emailChecker().isEmpty()
            },
            onClick = { emailValue = "" },
            focusManager = focusManager,
        )

        TextField(
            textFieldType = TextFieldType.Password,
            modifier = Modifier.fillMaxWidth(),
            enable = !isLoading,
            value = passwordValue,
            isValid = passwordValid,
            onValueChange = {
                passwordValue = it
                passwordValid = passwordValue.passwordChecker(isStrict = !isLogin).isEmpty()
            },
            onClick = { passwordVisible = !passwordVisible },
            focusManager = focusManager,
            isVisible = passwordVisible,
            isStrict = !isLogin
        )

        OutlinedButton(
            onClick = {
                if (isLogin) onClickLogin(emailValue, passwordValue)
                else onClickRegister(nameValue, emailValue, passwordValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            enabled = if (isLogin) emailValid && passwordValid && emailValue.isNotEmpty() && passwordValue.isNotEmpty() && !isLoading
            else nameValid && emailValid && passwordValid && nameValue.isNotEmpty() && emailValue.isNotEmpty() && passwordValue.isNotEmpty()
        ) { Text(text = if (isLogin) "Login" else "Register") }

        Row {
            Text(text = "${if (isLogin) "Don't" else "Already"} have an account? ")
            Text(
                text = if (isLogin) "Register" else "Login", modifier = Modifier.clickable {
                    if (!isLoading) {
                        focusManager.clearFocus()
                        nameValue = ""
                        emailValue = ""
                        passwordValue = ""
                        isLogin = !isLogin
                    }
                }, color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
