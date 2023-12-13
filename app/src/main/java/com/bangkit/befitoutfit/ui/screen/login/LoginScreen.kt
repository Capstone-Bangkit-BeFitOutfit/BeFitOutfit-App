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
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.helper.StringExtensions.emailChecker
import com.bangkit.befitoutfit.helper.StringExtensions.errorMessageHandler
import com.bangkit.befitoutfit.helper.StringExtensions.passwordChecker
import com.bangkit.befitoutfit.ui.component.ContentLogin

@Composable
fun LoginScreen(
    state: State<Login>,
    login: (String, String) -> Unit,
    navigateToMain: () -> Unit,
    navigateToRegister: () -> Unit,
    onStateResultFeedback: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var valueEmail by remember {
        mutableStateOf(
            value = "",
        )
    }
    var isValidEmail by remember {
        mutableStateOf(
            value = true,
        )
    }

    var valuePassword by remember {
        mutableStateOf(
            value = "",
        )
    }
    var isValidPassword by remember {
        mutableStateOf(
            value = true,
        )
    }
    var isVisiblePassword by remember {
        mutableStateOf(
            value = false,
        )
    }

    var success by remember {
        mutableStateOf(
            value = false,
        )
    }

    when (state) {
        is State.Success -> {
            if (!success) {
                navigateToMain()
                onStateResultFeedback(
                    "Successfully logged in",
                )
            }
            success = true
        }

        is State.Error -> onStateResultFeedback(
            state.message.errorMessageHandler(
                specific = "login",
            ),
        )

        else -> {}
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ContentLogin(
            valueEmail = valueEmail,
            isValidEmail = isValidEmail,
            onValueChangeEmail = {
                valueEmail = it
                isValidEmail = valueEmail.emailChecker().isEmpty()
            },
            onClickEmail = {
                valueEmail = ""
            },
            valuePassword = valuePassword,
            isValidPassword = isValidPassword,
            onValueChangePassword = {
                valuePassword = it
                isValidPassword = valuePassword.passwordChecker().isEmpty()
            },
            onClickPassword = {
                isVisiblePassword = !isVisiblePassword
            },
            isVisiblePassword = isVisiblePassword,
            onClickLogin = {
                login(valueEmail, valuePassword)
            },
            onClickRegister = {
                navigateToRegister()
            },
            enabled = state is State.Idle,
        )

        AnimatedVisibility(
            visible = state is State.Loading,
        ) {
            CircularProgressIndicator()
        }
    }
}
