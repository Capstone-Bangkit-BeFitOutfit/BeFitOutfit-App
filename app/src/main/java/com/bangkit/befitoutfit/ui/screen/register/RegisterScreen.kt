package com.bangkit.befitoutfit.ui.screen.register

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
import com.bangkit.befitoutfit.data.model.Info
import com.bangkit.befitoutfit.helper.InputChecker.emailChecker
import com.bangkit.befitoutfit.helper.InputChecker.passwordChecker
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.ui.component.ContentRegister

@Composable
fun RegisterScreen(
    state: State<Info>,
    register: (String, String, String) -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var nameValue by remember {
        mutableStateOf(
            value = "",
        )
    }
    var nameValid by remember {
        mutableStateOf(
            value = true,
        )
    }

    var emailValue by remember {
        mutableStateOf(
            value = "",
        )
    }
    var emailValid by remember {
        mutableStateOf(
            value = true,
        )
    }

    var passwordValue by remember {
        mutableStateOf(
            value = "",
        )
    }
    var passwordValid by remember {
        mutableStateOf(
            value = true,
        )
    }
    var passwordVisible by remember {
        mutableStateOf(
            value = false,
        )
    }

    when (state) {
        is State.Success -> navigateToLogin()
        is State.Error -> {/*TODO: register error handling*/
        }

        else -> {}
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ContentRegister(
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
                emailValid = emailValue.emailChecker().isEmpty()
            },
            onEmailClick = {
                emailValue = ""
            },
            passwordValue = passwordValue,
            passwordValid = passwordValid,
            onPasswordValueChange = {
                passwordValue = it
                passwordValid = passwordValue.passwordChecker(
                    isStrict = true,
                ).isEmpty()
            },
            onPasswordClick = {
                passwordVisible = !passwordVisible
            },
            isPasswordVisible = passwordVisible,
            onRegisterClick = {
                register(nameValue, emailValue, passwordValue)
            },
            onLoginClick = {
                navigateToLogin()
            },
            enable = state is State.Idle,
        )

        AnimatedVisibility(
            visible = state is State.Loading,
        ) {
            CircularProgressIndicator()
        }
    }
}
