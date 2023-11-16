package com.bangkit.befitoutfit.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(title = { Text(text = title) }, modifier = modifier, navigationIcon = {
        IconButton(onClick = { /*TODO: feature profile*/ }) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile")
        }
    }, actions = {
        IconButton(onClick = { /*TODO: feature logout*/ }) {
            Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout")
        }
    })
}
