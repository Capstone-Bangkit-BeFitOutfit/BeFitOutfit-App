package com.bangkit.befitoutfit.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector) {
    data object MyOutfit : Screen("My Outfit", Icons.Default.Checkroom)
    data object AddOutfit : Screen("Add Outfit", Icons.Default.AddAPhoto)
    data object Recommend : Screen("Recommend", Icons.Default.Recommend)
}
