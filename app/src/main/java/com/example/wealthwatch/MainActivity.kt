package com.example.wealthwatch

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.presentation.WealthWatchRoot
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Splash ekranı veri yüklenene kadar ekranda kalsın
        splashScreen.setKeepOnScreenCondition {
            mainViewModel.uiState.value.screenState == ScreenState.LOADING
        }

        // Uygulama açıldığında ana temaya geç
        setTheme(R.style.Theme_WealthWatch)

        setContent {
            val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState.language, uiState.screenState) {
                // Wait for the ViewModel to load real data
                if (uiState.screenState == ScreenState.LOADING) return@LaunchedEffect

                val currentLocales = AppCompatDelegate.getApplicationLocales()
                // Use safe logic: if currentLocales is empty, it follows system. 
                // We should check if what we WANT (uiState.language) matches what is ACTIVE.

                // Get the effective language code. If empty, use default (System)
                val activeLanguageCode = if (!currentLocales.isEmpty) {
                    currentLocales.get(0)?.language
                } else {
                    Locale.getDefault().language
                }

                // If they strictly match, do nothing to avoid loop or redundant set
                if (activeLanguageCode.equals(uiState.language.code, ignoreCase = true)) {
                    return@LaunchedEffect
                }

                val appLocale = LocaleListCompat.forLanguageTags(uiState.language.code)
                AppCompatDelegate.setApplicationLocales(appLocale)
            }

            WealthWatchRoot(
                appState = uiState,
                onUnlock = { mainViewModel.unlock() }
            )
        }
    }
}
