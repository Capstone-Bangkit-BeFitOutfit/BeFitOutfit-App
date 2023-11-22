package com.bangkit.befitoutfit

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.befitoutfit.BuildConfig.BASE_URL
import com.bangkit.befitoutfit.BuildConfig.BASE_URL_MOCK
import com.bangkit.befitoutfit.BuildConfig.DEBUG
import com.bangkit.befitoutfit.BuildConfig.MOCK
import com.bangkit.befitoutfit.data.local.preferences.SessionPreferences
import com.bangkit.befitoutfit.data.remote.ApiService
import com.bangkit.befitoutfit.data.repository.AuthRepository
import com.bangkit.befitoutfit.data.repository.OutfitRepository
import com.bangkit.befitoutfit.data.repository.RecommendRepository
import com.bangkit.befitoutfit.data.repository.SessionRepository
import com.bangkit.befitoutfit.ui.screen.addOutfit.AddOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.auth.AuthViewModel
import com.bangkit.befitoutfit.ui.screen.myOutfit.MyOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.profile.ProfileViewModel
import com.bangkit.befitoutfit.ui.screen.recommend.RecommendViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        /*TODO: remove Context.session type*/
        private val Context.session: DataStore<Preferences> by preferencesDataStore(name = "session")

        private val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(if (DEBUG) BODY else NONE)).build()

        val app = module {
            single { SessionPreferences(androidContext().session) }

            single {
                Retrofit.Builder().baseUrl(if (MOCK) BASE_URL_MOCK else BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(client).build()
                    .create(ApiService::class.java)
            }

            single { SessionRepository(get()) }

            single { AuthRepository(get()) }

            single { OutfitRepository(get()) }

            single { RecommendRepository(get(), get()) }

            viewModel { MainViewModel(get()) }

            viewModel { AuthViewModel(get(), get()) }

            viewModel { MyOutfitViewModel(get()) }

            viewModel { RecommendViewModel(get()) }

            viewModel { ProfileViewModel(get()) }

            viewModel { AddOutfitViewModel(get()) }
        }
    }
}
