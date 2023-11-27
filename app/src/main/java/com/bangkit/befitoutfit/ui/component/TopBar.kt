package com.bangkit.befitoutfit.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    onClickProfile: () -> Unit = {},
    onClickLogout: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onClickProfile,
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Profile",
                )
            }
        },
        actions = {
            IconButton(
                onClick = onClickLogout,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = "Logout",
                )
            }
        },
    )
}
