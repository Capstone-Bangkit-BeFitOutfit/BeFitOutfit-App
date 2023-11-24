package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bangkit.befitoutfit.helper.InputChecker.emailChecker
import com.bangkit.befitoutfit.helper.InputChecker.passwordChecker
import com.bangkit.befitoutfit.helper.TextFieldType

@Composable
fun TextField(
    textFieldType: TextFieldType,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    value: String = "",
    isValid: Boolean = true,
    onValueChange: (String) -> Unit = {},
    onClick: () -> Unit = {},
    focusManager: FocusManager? = null,
    imeAction: ImeAction = ImeAction.Next,
    isVisible: Boolean = false,
    isStrict: Boolean = false,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        enabled = enable,
        label = { Text(text = textFieldType.type) },
        placeholder = { Text(text = "Enter your ${textFieldType.type}") },
        leadingIcon = {
            Icon(
                imageVector = textFieldType.leadingIcon, contentDescription = textFieldType.type
            )
        },
        trailingIcon = {
            when (textFieldType) {
                TextFieldType.Password -> IconButton(onClick = onClick) {
                    Icon(
                        imageVector = if (isVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = "${if (isVisible) " Hide " else " Show "} ${textFieldType.type}"
                    )
                }

                else -> if (value.isNotEmpty()) IconButton(onClick = onClick) {
                    Icon(imageVector = Icons.Outlined.Cancel, contentDescription = "Cancel")
                }
            }
        },
        supportingText = {
            if (!isValid) Text(
                text = when {
                    value.isEmpty() -> "${textFieldType.type} is required"
                    textFieldType == TextFieldType.Email -> value.emailChecker()
                    textFieldType == TextFieldType.Password -> value.passwordChecker(isStrict = isStrict)
                    else -> ""
                }
            )
        },
        isError = !isValid,
        visualTransformation = if (textFieldType == TextFieldType.Password && !isVisible) PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = textFieldType.keyboardType, imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager?.moveFocus(FocusDirection.Down) },
            onDone = { focusManager?.clearFocus() }),
        singleLine = true
    )
}
