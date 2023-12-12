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
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.helper.StringExtensions.emailChecker
import com.bangkit.befitoutfit.helper.StringExtensions.errorMessageHandler
import com.bangkit.befitoutfit.helper.StringExtensions.passwordChecker
import com.bangkit.befitoutfit.ui.component.ContentRegister

@Composable
fun RegisterScreen(
    state: State<Info>,
    register: (String, String, String) -> Unit,
    navigateToLogin: () -> Unit,
    onStateResultFeedback: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var valueName by remember {
        mutableStateOf(
            value = "",
        )
    }
    var isValidName by remember {
        mutableStateOf(
            value = true,
        )
    }

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

    when (state) {
        is State.Success -> {
            navigateToLogin()
            onStateResultFeedback(
                "Successfully created",
            )
        }

        is State.Error -> onStateResultFeedback(
            state.message.errorMessageHandler(
                specific = "register",
            ),
        )

        else -> {}
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ContentRegister(
            valueName = valueName,
            isValidName = isValidName,
            onValueChangeName = {
                isValidName = it.isNotEmpty()
                valueName = it
            },
            onClickName = {
                valueName = ""
            },
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
                isValidPassword = valuePassword.passwordChecker(
                    isStrict = true,
                ).isEmpty()
            },
            onClickPassword = {
                isVisiblePassword = !isVisiblePassword
            },
            isVisiblePassword = isVisiblePassword,
            onClickRegister = {
                register(valueName, valueEmail, valuePassword)
            },
            onClickLogin = {
                navigateToLogin()
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
