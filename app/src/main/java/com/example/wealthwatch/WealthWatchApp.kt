package com.example.wealthwatch

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import androidx.appcompat.app.AppCompatDelegate

import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository

@HiltAndroidApp
class WealthWatchApp : Application(), ImageLoaderFactory {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate() {
        super.onCreate()

        // Sync Theme Preference Immediately using Repository
        val theme = settingsRepository.getCurrentTheme()

        val mode = when (theme) {
            WWTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            WWTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            WWTheme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun newImageLoader(): ImageLoader = imageLoader
}