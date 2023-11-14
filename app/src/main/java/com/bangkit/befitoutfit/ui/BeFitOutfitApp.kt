package com.bangkit.befitoutfit.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.befitoutfit.ui.theme.BeFitOutfitTheme

@Composable
fun BeFitOutfitApp(modifier: Modifier = Modifier) {
    Text(text = "Hello", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun BeFitOutfitAppPreview() {
    BeFitOutfitTheme { BeFitOutfitApp() }
}
