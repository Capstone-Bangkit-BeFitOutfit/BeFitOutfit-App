package com.bangkit.befitoutfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.ui.BeFitOutfitApp
import com.bangkit.befitoutfit.ui.screen.Screen
import com.bangkit.befitoutfit.ui.theme.BeFitOutfitTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(
    ExperimentalMaterial3Api::class,
    KoinExperimentalAPI::class,
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeFitOutfitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    KoinAndroidContext {
                        val viewModel: MainViewModel = koinViewModel()
                        val flowSession = viewModel.getSession()
                        BeFitOutfitApp(
                            startDestination = if (runBlocking { flowSession.first().email.isNotEmpty() }) Screen.Main.route else Screen.Auth.route,
                            session = flowSession.collectAsState(initial = Session()).value,
                            clearSession = viewModel::clearSession,
                        )
                    }
                }
            }
        }
    }
}
