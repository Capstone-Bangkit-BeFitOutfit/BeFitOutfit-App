package com.bangkit.befitoutfit.ui.screen.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bangkit.befitoutfit.data.model.Login
import com.bangkit.befitoutfit.helper.InputChecker.emailChecker
import com.bangkit.befitoutfit.helper.InputChecker.passwordChecker
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.ui.component.ContentLogin

@Composable
fun LoginScreen(
    state: State<Login>,
    login: (String, String) -> Unit,
    navigateToMain: () -> Unit,
    navigateToRegister: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var emailValue by remember { mutableStateOf("") }
    var emailValid by remember { mutableStateOf(true) }

    var passwordValue by remember { mutableStateOf("") }
    var passwordValid by remember { mutableStateOf(true) }
    var passwordVisible by remember { mutableStateOf(false) }

    when (state) {
        is State.Success -> navigateToMain()
        is State.Error -> {/*TODO: login error handling*/
        }

        else -> {}
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ContentLogin(
            emailValue = emailValue,
            emailValid = emailValid,
            onEmailValueChange = {
                emailValue = it
                emailValid = emailValue.emailChecker().isEmpty()
            },
            onEmailClick = { emailValue = "" },
            passwordValue = passwordValue,
            passwordValid = passwordValid,
            onPasswordValueChange = {
                passwordValue = it
                passwordValid = passwordValue.passwordChecker().isEmpty()
            },
            onPasswordClick = { passwordVisible = !passwordVisible },
            isPasswordVisible = passwordVisible,
            onLoginClick = { login(emailValue, passwordValue) },
            onRegisterClick = { navigateToRegister() },
            enable = state is State.Idle,
        )

        AnimatedVisibility(visible = state is State.Loading) { CircularProgressIndicator() }
    }
}
