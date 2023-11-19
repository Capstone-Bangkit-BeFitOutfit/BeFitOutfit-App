package com.bangkit.befitoutfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.bangkit.befitoutfit.data.model.Session
import com.bangkit.befitoutfit.ui.BeFitOutfitApp
import com.bangkit.befitoutfit.ui.theme.BeFitOutfitTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
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
                        BeFitOutfitApp(
                            isLoggedIn = runBlocking {
                                viewModel.getSession().first().email.isNotEmpty()
                            },
                            session = viewModel.getSession()
                                .collectAsState(initial = Session(name = "", email = "")).value,
                            setSession = viewModel::setSession,
                            logout = viewModel::clearSession
                        )
                    }
                }
            }
        }
    }
}
