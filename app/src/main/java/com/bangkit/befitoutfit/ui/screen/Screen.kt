package com.bangkit.befitoutfit.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Recommend
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector = Icons.Outlined.Circle,
    val fabTitle: String = "",
    val fabIcon: ImageVector = Icons.Outlined.Circle,
) {
    data object Auth : Screen(route = "Auth")
    data object Main : Screen(route = "Main")
    data object MyOutfit : Screen(
        route = "My Outfit",
        icon = Icons.Outlined.Checkroom,
        fabTitle = "Add Outfit",
        fabIcon = Icons.Outlined.AddAPhoto
    )

    data object Recommend : Screen(
        route = "Recommend",
        icon = Icons.Outlined.Recommend,
        fabTitle = "Setting Recommend",
        fabIcon = Icons.Outlined.Settings
    )
}
