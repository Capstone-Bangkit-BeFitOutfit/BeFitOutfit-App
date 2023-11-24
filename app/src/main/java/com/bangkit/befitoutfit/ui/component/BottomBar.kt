package com.bangkit.befitoutfit.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.bangkit.befitoutfit.ui.screen.Screen

@Composable
fun BottomBar(
    screens: List<Screen>,
    currentRoute: String,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        screens.forEach {
            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = {
                    navController.apply {
                        navigate(it.route) {
                            popUpTo(graph.findStartDestination().id) { saveState = true }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.route,
                    )
                },
                label = {
                    Text(
                        text = it.route,
                    )
                },
            )
        }
    }
}
