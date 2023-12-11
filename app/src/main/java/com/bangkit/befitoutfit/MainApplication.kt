package com.bangkit.befitoutfit

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.befitoutfit.BuildConfig.BASE_URL
import com.bangkit.befitoutfit.BuildConfig.BASE_URL_MOCK
import com.bangkit.befitoutfit.BuildConfig.DEBUG
import com.bangkit.befitoutfit.BuildConfig.MOCK
import com.bangkit.befitoutfit.data.local.preferences.SessionPreferences
import com.bangkit.befitoutfit.data.local.preferences.SettingPreferences
import com.bangkit.befitoutfit.data.remote.ApiService
import com.bangkit.befitoutfit.data.repository.AuthRepository
import com.bangkit.befitoutfit.data.repository.OutfitRepository
import com.bangkit.befitoutfit.data.repository.RecommendRepository
import com.bangkit.befitoutfit.data.repository.SessionRepository
import com.bangkit.befitoutfit.data.repository.SettingRepository
import com.bangkit.befitoutfit.data.repository.UserRepository
import com.bangkit.befitoutfit.ui.screen.addOutfit.AddOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.detailOutfit.DetailOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.login.LoginViewModel
import com.bangkit.befitoutfit.ui.screen.myOutfit.MyOutfitViewModel
import com.bangkit.befitoutfit.ui.screen.profile.ProfileViewModel
import com.bangkit.befitoutfit.ui.screen.recommend.RecommendViewModel
import com.bangkit.befitoutfit.ui.screen.register.RegisterViewModel
import com.bangkit.befitoutfit.ui.screen.settingRecommend.SettingRecommendViewModel
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
            androidContext(
                androidContext = this@MainApplication,
            )
            modules(
                modules = listOf(
                    preferencesModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                ),
            )
        }
    }

    companion object {
        private val Context.session by preferencesDataStore(
            name = "session",
        )
        private val Context.setting by preferencesDataStore(
            name = "setting",
        )

        val preferencesModule = module {
            single {
                SessionPreferences(
                    datastore = androidContext().session,
                )
            }

            single {
                SettingPreferences(
                    datastore = androidContext().setting,
                )
            }
        }

        val networkModule = module {
            single {
                Retrofit.Builder().baseUrl(if (MOCK) BASE_URL_MOCK else BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(
                        OkHttpClient.Builder()
                            .addInterceptor(HttpLoggingInterceptor().setLevel(if (DEBUG) BODY else NONE))
                            .build()
                    ).build().create(ApiService::class.java)
            }
        }

        val repositoryModule = module {
            single {
                AuthRepository(
                    apiService = get(),
                )
            }

            single {
                OutfitRepository(
                    sessionPreferences = get(),
                    apiService = get(),
                )
            }

            single {
                RecommendRepository(
                    sessionPreferences = get(),
                    settingPreferences = get(),
                    apiService = get(),
                )
            }

            single {
                SessionRepository(
                    sessionPreferences = get(),
                )
            }

            single {
                SettingRepository(
                    settingPreferences = get(),
                )
            }

            single {
                UserRepository(
                    sessionPreferences = get(),
                    apiService = get(),
                )
            }
        }

        val viewModelModule = module {
            viewModel {
                AddOutfitViewModel(
                    outfitRepository = get(),
                )
            }

            viewModel {
                DetailOutfitViewModel(
                    outfitRepository = get(),
                )
            }

            viewModel {
                LoginViewModel(
                    authRepository = get(),
                    sessionRepository = get(),
                )
            }

            viewModel {
                MainViewModel(
                    sessionRepository = get(),
                )
            }

            viewModel {
                MyOutfitViewModel(
                    outfitRepository = get(),
                )
            }

            viewModel {
                ProfileViewModel(
                    sessionRepository = get(),
                    userRepository = get(),
                )
            }

            viewModel {
                RecommendViewModel(
                    recommendRepository = get(),
                )
            }

            viewModel {
                RegisterViewModel(
                    authRepository = get(),
                )
            }

            viewModel {
                SettingRecommendViewModel(
                    settingRepository = get(),
                )
            }
        }
    }
}
