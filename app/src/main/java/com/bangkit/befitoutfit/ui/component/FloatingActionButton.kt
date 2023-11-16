package com.bangkit.befitoutfit.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bangkit.befitoutfit.ui.screen.Screen

@Composable
fun FloatingActionButton(
    currentRoute: String,
    onClick: () -> Unit,
) {
    when (currentRoute) {
        Screen.Recommend.route -> ExtendedFloatingActionButton(
            text = { Text(text = "Setting") },
            icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "Setting") },
            onClick = onClick
        )
    }
}
