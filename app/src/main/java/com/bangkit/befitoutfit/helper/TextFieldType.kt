package com.bangkit.befitoutfit.helper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType

sealed class TextFieldType(
    val type: String,
    val leadingIcon: ImageVector,
    val keyboardType: KeyboardType,
) {
    data object Name : TextFieldType("Name", Icons.Outlined.AccountCircle, KeyboardType.Text)
    data object Email : TextFieldType("Email", Icons.Outlined.Mail, KeyboardType.Email)
    data object Password : TextFieldType("Password", Icons.Outlined.Lock, KeyboardType.Password)
}
