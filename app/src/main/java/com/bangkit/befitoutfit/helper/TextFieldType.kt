package com.bangkit.befitoutfit.helper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType

sealed class TextFieldType(
    val type: String,
    val leadingIcon: ImageVector,
    val keyboardType: KeyboardType,
) {
    data object Name : TextFieldType(
        type = "Name", leadingIcon = Icons.Outlined.AccountCircle, keyboardType = KeyboardType.Text
    )

    data object OutfitName : TextFieldType(
        type = "Outfit name",
        leadingIcon = Icons.Outlined.Checkroom,
        keyboardType = KeyboardType.Text
    )

    data object Email : TextFieldType(
        type = "Email", leadingIcon = Icons.Outlined.Mail, keyboardType = KeyboardType.Email
    )

    data object Password : TextFieldType(
        type = "Password", leadingIcon = Icons.Outlined.Lock, keyboardType = KeyboardType.Password
    )
}
