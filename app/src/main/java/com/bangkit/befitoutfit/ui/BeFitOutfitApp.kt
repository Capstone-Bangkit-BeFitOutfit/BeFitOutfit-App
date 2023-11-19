package com.bangkit.befitoutfit.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.navigation
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.helper.BottomSheetType
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.ui.component.BottomBar
import com.bangkit.befitoutfit.ui.component.BottomSheet
import com.bangkit.befitoutfit.ui.component.FloatingActionButton
import com.bangkit.befitoutfit.ui.component.TopBar
import com.bangkit.befitoutfit.ui.screen.Screen
import com.bangkit.befitoutfit.ui.screen.auth.AuthScreen
import com.bangkit.befitoutfit.ui.screen.auth.AuthViewModel
import com.bangkit.befitoutfit.ui.theme.BeFitOutfitTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeFitOutfitApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    isLoggedIn: Boolean = false,
    session: Session = Session(email = "", name = ""),
    setSession: (Session) -> Unit = {},
    logout: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    val startDestination = if (isLoggedIn) Screen.Main.route else Screen.Auth.route
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route ?: startDestination

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetType by remember { mutableStateOf<BottomSheetType>(BottomSheetType.Profile) }

    Scaffold(modifier = modifier, topBar = {
        if (currentRoute != Screen.Auth.route) TopBar(title = currentRoute, profile = {
            bottomSheetType = BottomSheetType.Profile
            showBottomSheet = true
        }, logout = {
            logout()
            navController.navigate(Screen.Auth.route) { navController.popBackStack() }
        })
    }, bottomBar = {
        if (currentRoute != Screen.Auth.route) BottomBar(
            screens = listOf(Screen.MyOutfit, Screen.AddOutfit, Screen.Recommend),
            currentRoute = currentRoute,
            navController = navController
        )
    }, floatingActionButton = {
        FloatingActionButton(currentRoute = currentRoute, onClick = {
            when (currentRoute) {
                Screen.Recommend.route -> bottomSheetType = BottomSheetType.SettingRecommend
            }
            showBottomSheet = true
        })
    }) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(it),
        ) {
            composable(route = Screen.Auth.route) {
                val viewModel: AuthViewModel = koinViewModel()
                AuthScreen(stateLogin = viewModel.stateLogin.collectAsState(initial = State.Idle).value,
                    stateRegister = viewModel.stateRegister.collectAsState(initial = State.Idle).value,
                    onClickLogin = viewModel::login,
                    onClickRegister = viewModel::register,
                    navigateToMain = { navController.navigate(Screen.Main.route) { navController.popBackStack() } })
            }
            navigation(startDestination = Screen.MyOutfit.route, route = Screen.Main.route) {
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
        }

        BottomSheet(
            showBottomSheet = showBottomSheet,
            bottomSheetType = bottomSheetType,
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            session = session,
            onClickDismiss = {
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion { if (!sheetState.isVisible) showBottomSheet = false }
            },
            onClickProfile = setSession
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BeFitOutfitAppPreview() {
    BeFitOutfitTheme { BeFitOutfitApp() }
}
