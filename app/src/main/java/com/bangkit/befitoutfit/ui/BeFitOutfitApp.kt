package com.bangkit.befitoutfit.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.bangkit.befitoutfit.data.model.Outfit
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
import com.bangkit.befitoutfit.ui.screen.myOutfit.MyOutfitScreen
import com.bangkit.befitoutfit.ui.screen.myOutfit.MyOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.recommend.RecommendScreen
import com.bangkit.befitoutfit.ui.screen.recommend.RecommendViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeFitOutfitApp(
    isLoggedIn: Boolean,
    session: Session,
    clearSession: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {/*TODO: make BeFitOutfitApp stateless*/
    val scope = rememberCoroutineScope()

    val startDestination = if (isLoggedIn) Screen.Main.route else Screen.Auth.route
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route ?: startDestination

    /*TODO: make skip partially expanded*/
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetType by remember { mutableStateOf<BottomSheetType>(BottomSheetType.Profile) }

    var selectedOutfit by remember { mutableStateOf(Outfit()) }

    Scaffold(modifier = modifier, topBar = {
        if (currentRoute != Screen.Auth.route) TopBar(title = currentRoute, profile = {
            bottomSheetType = BottomSheetType.Profile
            showBottomSheet = true
        }, logout = {
            clearSession()
            navController.navigate(Screen.Auth.route) { navController.popBackStack() }
        })
    }, bottomBar = {
        if (currentRoute != Screen.Auth.route) BottomBar(
            screens = listOf(Screen.MyOutfit, Screen.Recommend),
            currentRoute = currentRoute,
            navController = navController
        )
    }, floatingActionButton = {
        if (currentRoute != Screen.Auth.route) FloatingActionButton(
            currentRoute = currentRoute,
            onClick = {
                when (currentRoute) {
                    Screen.MyOutfit.route -> bottomSheetType = BottomSheetType.AddOutfit
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
                    val viewModel: MyOutfitViewModel = koinViewModel()
                    MyOutfitScreen(state = viewModel.state.collectAsState(initial = State.Idle).value,
                        getOutfit = viewModel::getOutfit,
                        detailOutfit = { outfit ->
                            selectedOutfit = outfit
                            bottomSheetType = BottomSheetType.DetailOutfit
                            showBottomSheet = true
                        })
                }
                composable(route = Screen.Recommend.route) {
                    val viewModel: RecommendViewModel = koinViewModel()
                    RecommendScreen(
                        state = viewModel.state.collectAsState(initial = State.Idle).value,
                        getRecommend = viewModel::getRecommend,
                        detailRecommend = {
                            bottomSheetType = BottomSheetType.DetailOutfit
                            showBottomSheet = true
                        },
                    )
                }
            }
        }

        BottomSheet(
            showBottomSheet = showBottomSheet,
            bottomSheetType = bottomSheetType,
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            session = session,
            outfit = selectedOutfit,
            onClickDismiss = {
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion { if (!sheetState.isVisible) showBottomSheet = false }
            },
        )
    }
}
