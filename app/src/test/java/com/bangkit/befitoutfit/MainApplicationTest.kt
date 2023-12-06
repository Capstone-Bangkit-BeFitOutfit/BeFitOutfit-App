package com.bangkit.befitoutfit

import androidx.datastore.core.DataStore
import com.bangkit.befitoutfit.MainApplication.Companion.networkModule
import com.bangkit.befitoutfit.MainApplication.Companion.preferencesModule
import com.bangkit.befitoutfit.MainApplication.Companion.repositoryModule
import com.bangkit.befitoutfit.MainApplication.Companion.viewModelModule
import com.bangkit.befitoutfit.data.local.preferences.SessionPreferences
import com.bangkit.befitoutfit.data.local.preferences.SettingPreferences
import com.bangkit.befitoutfit.data.remote.ApiService
import com.bangkit.befitoutfit.data.repository.AuthRepository
import com.bangkit.befitoutfit.data.repository.OutfitRepository
import com.bangkit.befitoutfit.data.repository.RecommendRepository
import com.bangkit.befitoutfit.data.repository.SessionRepository
import com.bangkit.befitoutfit.data.repository.SettingRepository
import com.bangkit.befitoutfit.data.repository.UserRepository
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

@OptIn(
    KoinExperimentalAPI::class,
)
class MainApplicationTest : KoinTest {
    @Test
    fun `network module should verified`() {
        networkModule.verify()
    }

    @Test
    fun `preferences module should verified`() {
        preferencesModule.verify(
            extraTypes = listOf(
                DataStore::class,
            ),
        )
    }

    @Test
    fun `repository module should verified`() {
        repositoryModule.verify(
            extraTypes = listOf(
                SessionPreferences::class,
                SettingPreferences::class,
                ApiService::class,
            ),
        )
    }

    @Test
    fun `view model module should verified`() {
        viewModelModule.verify(
            extraTypes = listOf(
                AuthRepository::class,
                OutfitRepository::class,
                RecommendRepository::class,
                SessionRepository::class,
                SettingRepository::class,
                UserRepository::class,
            ),
        )
    }
}
