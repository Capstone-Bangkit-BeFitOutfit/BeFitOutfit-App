package com.bangkit.befitoutfit.ui.component

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bangkit.befitoutfit.ui.screen.Screen

@Composable
fun FloatingActionButton(
    currentRoute: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    when (currentRoute) {
        Screen.MyOutfit.route -> Screen.MyOutfit
        else -> Screen.Recommend
    }.apply {
        ExtendedFloatingActionButton(
            text = {
                Text(
                    text = fabTitle,
                )
            },
            icon = {
                Icon(
                    imageVector = fabIcon,
                    contentDescription = fabTitle,
                )
            },
            onClick = onClick,
            modifier = modifier,
        )
    }

}
