package com.bangkit.befitoutfit.ui

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
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
import com.bangkit.befitoutfit.ui.component.BottomBar
import com.bangkit.befitoutfit.ui.component.BottomSheet
import com.bangkit.befitoutfit.ui.component.FloatingActionButton
import com.bangkit.befitoutfit.ui.component.TopBar
import com.bangkit.befitoutfit.ui.screen.Screen
import com.bangkit.befitoutfit.ui.screen.login.LoginScreen
import com.bangkit.befitoutfit.ui.screen.login.LoginViewModel
import com.bangkit.befitoutfit.ui.screen.myOutfit.MyOutfitScreen
import com.bangkit.befitoutfit.ui.screen.myOutfit.MyOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.recommend.RecommendScreen
import com.bangkit.befitoutfit.ui.screen.recommend.RecommendViewModel
import com.bangkit.befitoutfit.ui.screen.register.RegisterScreen
import com.bangkit.befitoutfit.ui.screen.register.RegisterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun BeFitOutfitApp(
    context: Context,
    startDestination: String,
    session: Session,
    clearSession: () -> Unit,
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    hostState: SnackbarHostState = remember {
        SnackbarHostState()
    },
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    currentRoute: String = navController.currentBackStackEntryAsState().value?.destination?.route
        ?: startDestination,
    isAuthScreens: Boolean = listOf(
        Screen.Login.route, Screen.Register.route
    ).contains(currentRoute),
) {
    var showBottomSheet by remember {
        mutableStateOf(
            value = false,
        )
    }

    var bottomSheetType by remember {
        mutableStateOf<BottomSheetType>(
            value = BottomSheetType.Profile
        )
    }

    var selectedOutfit by remember {
        mutableStateOf(
            value = Outfit(),
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (isAuthScreens.not()) TopBar(
                title = currentRoute,
                onClickProfile = {
                    bottomSheetType = BottomSheetType.Profile
                    showBottomSheet = true
                },
                onClickLogout = {
                    clearSession()
                    navController.popBackStack()
                    navController.navigate(Screen.Auth.route)
                },
            )
        },
        bottomBar = {
            if (isAuthScreens.not()) BottomBar(
                screens = listOf(Screen.MyOutfit, Screen.Recommend),
                currentRoute = currentRoute,
                navController = navController,
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = hostState,
            )
        },
        floatingActionButton = {
            if (isAuthScreens.not()) FloatingActionButton(
                currentRoute = currentRoute,
                onClick = {
                    when (currentRoute) {
                        Screen.MyOutfit.route -> bottomSheetType = BottomSheetType.AddOutfit
                        Screen.Recommend.route -> bottomSheetType = BottomSheetType.SettingRecommend
                    }
                    showBottomSheet = true
                },
            )
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(
                paddingValues = it,
            ),
        ) {
            navigation(
                startDestination = Screen.Login.route,
                route = Screen.Auth.route,
            ) {
                composable(
                    route = Screen.Login.route,
                ) {
                    val viewModel: LoginViewModel = koinViewModel()
                    LoginScreen(
                        state = viewModel.state.collectAsState().value,
                        login = viewModel::login,
                        navigateToMain = {
                            navController.popBackStack()
                            navController.navigate(Screen.Main.route)
                        },
                        navigateToRegister = {
                            navController.navigate(Screen.Register.route)
                        },
                        onError = {
                            scope.launch {
                                hostState.showSnackbar(it)
                            }
                        },
                    )
                }
                composable(
                    route = Screen.Register.route,
                ) {
                    val viewModel: RegisterViewModel = koinViewModel()
                    RegisterScreen(
                        state = viewModel.state.collectAsState().value,
                        register = viewModel::register,
                        navigateToLogin = {
                            navController.navigateUp()
                        },
                        onError = {
                            scope.launch {
                                hostState.showSnackbar(it)
                            }
                        },
                    )
                }
            }
            navigation(
                startDestination = Screen.MyOutfit.route,
                route = Screen.Main.route,
            ) {
                composable(
                    route = Screen.MyOutfit.route,
                ) {
                    val viewModel: MyOutfitViewModel = koinViewModel()
                    MyOutfitScreen(
                        state = viewModel.state.collectAsState().value,
                        outfits = viewModel.outfits,
                        onRefresh = viewModel::getOutfit,
                        onError = {
                            scope.launch {
                                hostState.showSnackbar(it)
                            }
                        },
                        onClickDetailOutfit = { outfit ->
                            selectedOutfit = outfit
                            bottomSheetType = BottomSheetType.DetailOutfit
                            showBottomSheet = true
                        },
                    )
                }
                composable(
                    route = Screen.Recommend.route,
                ) {
                    val viewModel: RecommendViewModel = koinViewModel()
                    RecommendScreen(
                        state = viewModel.state.collectAsState().value,
                        recommend = viewModel.recommend,
                        onRefresh = viewModel::getRecommend,
                        onError = {
                            scope.launch {
                                hostState.showSnackbar(it)
                            }
                        },
                        onClickDetailOutfit = { outfit ->
                            selectedOutfit = outfit
                            bottomSheetType = BottomSheetType.DetailOutfit
                            showBottomSheet = true
                        },
                    )
                }
            }
        }

        BottomSheet(
            context = context,
            showBottomSheet = showBottomSheet,
            bottomSheetType = bottomSheetType,
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            session = session,
            outfit = selectedOutfit,
            onError = {
                scope.launch {
                    hostState.showSnackbar(it)
                }
            },
            dismiss = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) showBottomSheet = false
                }
            },
        )
    }
}
