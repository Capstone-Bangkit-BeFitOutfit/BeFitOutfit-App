package com.bangkit.befitoutfit.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.befitoutfit.ui.component.BottomBar
import com.bangkit.befitoutfit.ui.component.BottomSheet
import com.bangkit.befitoutfit.ui.component.FloatingActionButton
import com.bangkit.befitoutfit.ui.component.TopBar
import com.bangkit.befitoutfit.ui.screen.Screen
import com.bangkit.befitoutfit.ui.theme.BeFitOutfitTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeFitOutfitApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.MyOutfit.route,
) {
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route ?: startDestination
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(modifier = modifier, topBar = { TopBar(title = currentRoute) }, bottomBar = {
        BottomBar(
            screens = listOf(Screen.MyOutfit, Screen.AddOutfit, Screen.Recommend),
            currentRoute = currentRoute,
            navController = navController
        )
    }, floatingActionButton = {
        FloatingActionButton(currentRoute = currentRoute, onClick = { showBottomSheet = true })
    }) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(it),
        ) {
            composable(route = Screen.MyOutfit.route) {
                /*TODO: feature my outfit*/
                Text(text = Screen.MyOutfit.route)
            }
            composable(route = Screen.AddOutfit.route) {
                /*TODO: feature add outfit*/
                Text(text = Screen.AddOutfit.route)
            }
            composable(route = Screen.Recommend.route) {
                /*TODO: feature recommend*/
                Text(text = Screen.Recommend.route)
            }
        }

        BottomSheet(showBottomSheet = showBottomSheet,
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            currentRoute = currentRoute,
            onClick = {
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion { if (!sheetState.isVisible) showBottomSheet = false }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun BeFitOutfitAppPreview() {
    BeFitOutfitTheme { BeFitOutfitApp() }
}
