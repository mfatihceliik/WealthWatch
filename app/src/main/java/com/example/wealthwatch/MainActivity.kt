package com.example.wealthwatch

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.lock.LockScreen
import com.example.wealthwatch.presentation.main.MainScreen
import com.example.wealthwatch.presentation.main.MainViewModel
import com.example.wealthwatch.ui.theme.WealthWatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition {
            mainViewModel.uiState.value.screenState == ScreenState.LOADING
        }

        setContent {
            val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

            WealthWatchTheme {
                if (uiState.isLocked) {
                    LockScreen(onUnlock = mainViewModel::unlock)
                } else {
                    MainScreen()
                }
            }
        }
    }
}
