package com.example.wealthwatch

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WealthWatchApp : Application(), ImageLoaderFactory {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate() {
        super.onCreate()

        settingsRepository.applyAppConfigs()
    }

    override fun newImageLoader(): ImageLoader = imageLoader
}
