package com.bangkit.befitoutfit

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.befitoutfit.data.local.preferences.SessionPreferences
import com.bangkit.befitoutfit.data.repository.AuthRepository
import com.bangkit.befitoutfit.data.repository.SessionRepository
import com.bangkit.befitoutfit.ui.screen.auth.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(app)
        }
    }

    companion object {
        private val Context.session: DataStore<Preferences> by preferencesDataStore(name = "session")

        val app = module {
            single { SessionPreferences(androidContext().session) }

            single { SessionRepository(get()) }

            single { AuthRepository() }

            viewModel { MainViewModel(get()) }

            viewModel { AuthViewModel(get(), get()) }
        }
    }
}
