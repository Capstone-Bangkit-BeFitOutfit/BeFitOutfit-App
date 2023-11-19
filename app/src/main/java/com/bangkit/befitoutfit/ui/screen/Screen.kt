package com.bangkit.befitoutfit.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector = Icons.Default.Circle,
    val fabTitle: String = "",
    val fabIcon: ImageVector = Icons.Default.Circle,
) {
    data object Auth : Screen(route = "Auth")
    data object Main : Screen(route = "Main")
    data object MyOutfit : Screen(
        route = "My Outfit",
        icon = Icons.Default.Checkroom,
        fabTitle = "Add Outfit",
        fabIcon = Icons.Default.AddAPhoto
    )

    data object Recommend : Screen(
        route = "Recommend",
        icon = Icons.Default.Recommend,
        fabTitle = "Setting Recommend",
        fabIcon = Icons.Default.Settings
    )
}
