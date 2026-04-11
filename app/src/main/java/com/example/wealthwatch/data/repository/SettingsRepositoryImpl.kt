package com.example.wealthwatch.data.repository

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.os.LocaleListCompat
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.model.settings.AppLanguage
import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val prefs =
        context.getSharedPreferences("com.example.wealthwatch", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_THEME = "app_theme"
        private const val KEY_CURRENCY = "app_currency"
        private const val KEY_LANGUAGE = "app_language"
        private const val KEY_EXCHANGE_RATES = "exchange_rates_json"
        private const val KEY_APP_LOCK_ENABLED = "app_lock_enabled"
        private const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
        private const val KEY_PIN_HASH = "pin_hash"
    }

    // Initialize with defaults immediately to avoid blocking Main Thread
    private val _language = MutableStateFlow(AppLanguage.ENGLISH)
    private val _exchangeRates = MutableStateFlow<Map<String, Double>>(emptyMap())
    private val _currency = MutableStateFlow(AppCurrency.USD)
    private val _theme = MutableStateFlow(WWTheme.SYSTEM)
    private val _appLockEnabled = MutableStateFlow(false)
    private val _biometricEnabled = MutableStateFlow(false)

    init {
        // Load primitives synchronously to ensure UI has correct state 
        // immediately upon creation (avoiding race conditions with Activity recreation)
        loadPreferences()
    }

    private fun loadPreferences() {
        // Theme
        val themeName = prefs.getString(KEY_THEME, WWTheme.SYSTEM.name) ?: WWTheme.SYSTEM.name
        val theme = WWTheme.entries.find { it.name == themeName } ?: WWTheme.SYSTEM
        _theme.value = theme

        // Currency
        val currencyCode = prefs.getString(KEY_CURRENCY, AppCurrency.USD.code)
        val currency = AppCurrency.entries.find { it.code == currencyCode } ?: AppCurrency.USD
        _currency.value = currency

        // Exchange Rates
        _exchangeRates.value = loadExchangeRatesFromJson()

        // App Lock
        _appLockEnabled.value = prefs.getBoolean(KEY_APP_LOCK_ENABLED, false)
        _biometricEnabled.value = prefs.getBoolean(KEY_BIOMETRIC_ENABLED, false)

        // Language - Careful with Locale operations
        val savedLanguageCode = prefs.getString(KEY_LANGUAGE, null)
        val resolvedLanguage = if (savedLanguageCode != null) {
            AppLanguage.entries.find { it.code.equals(savedLanguageCode, ignoreCase = true) } 
                ?: AppLanguage.ENGLISH
        } else {
            // Determine default from system if not set
            // Note: Locale.getDefault() is fast enough, but we should be careful calling
            // AppCompatDelegate.getApplicationLocales() on background thread if it's not thread safe.
            // However, since we are in IO context, let's stick to safe kotlin locale check for defaults.
            val sysLocale = Locale.getDefault()
             if (sysLocale.language.equals("tr", ignoreCase = true)) AppLanguage.TURKISH else AppLanguage.ENGLISH
        }
        _language.value = resolvedLanguage
    }


    override fun getTheme(): Flow<WWTheme> = _theme.asStateFlow()

    override fun getCurrentTheme(): WWTheme = _theme.value

    override suspend fun setTheme(theme: WWTheme) {
        withContext(Dispatchers.IO) {
            prefs.edit { putString(KEY_THEME, theme.name) }
        }
        _theme.emit(theme)
        applyTheme(theme)
    }

    private fun applyTheme(theme: WWTheme) {
        val mode = when (theme) {
            WWTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            WWTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            WWTheme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun applyAppConfigs() {
        applyTheme(_theme.value)
        applyLocale(_language.value)
    }

    override suspend fun setLanguage(language: AppLanguage) {
        withContext(Dispatchers.IO) {
            prefs.edit { putString(KEY_LANGUAGE, language.code) }
        }
        _language.emit(language)
        applyLocale(language)
    }

    private fun applyLocale(language: AppLanguage) {
        val appLocale = LocaleListCompat.forLanguageTags(language.code)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    override fun getLanguage(): Flow<AppLanguage> = _language.asStateFlow()

    override fun getCurrentLanguage(): AppLanguage = _language.value

    override fun getCurrency(): Flow<AppCurrency> = _currency.asStateFlow()

    override suspend fun setCurrency(currency: AppCurrency) {
        withContext(Dispatchers.IO) {
            prefs.edit { putString(KEY_CURRENCY, currency.code) }
        }
        _currency.emit(currency)
    }

    override fun getExchangeRates(): Flow<Map<String, Double>> = _exchangeRates.asStateFlow()

    override suspend fun saveExchangeRates(rates: Map<String, Double>) {
        if (rates.isEmpty()) return

        withContext(Dispatchers.IO) {
            val currentMap = _exchangeRates.value.toMutableMap()
            currentMap.putAll(rates)

            _exchangeRates.emit(currentMap)

            val jsonObject = JSONObject()
            currentMap.forEach { (key, value) ->
                jsonObject.put(key, value)
            }
            prefs.edit { putString(KEY_EXCHANGE_RATES, jsonObject.toString()) }
        }
    }

    override suspend fun getLastKnownRates(): Map<String, Double> = _exchangeRates.value

    private fun loadExchangeRatesFromJson(): Map<String, Double> {
        val jsonString = prefs.getString(KEY_EXCHANGE_RATES, null) ?: return emptyMap()
        val result = mutableMapOf<String, Double>()

        return try {
            val jsonObject = JSONObject(jsonString)
            val keys = jsonObject.keys()

            while (keys.hasNext()) {
                val key = keys.next() as String
                val value = jsonObject.getDouble(key)
                result[key] = value
            }
            result
        } catch (e: Exception) {
            e.printStackTrace()
            emptyMap() 
        }
    }

    override fun getAppLockEnabled(): Flow<Boolean> = _appLockEnabled.asStateFlow()

    override suspend fun setAppLockEnabled(enabled: Boolean) {
        withContext(Dispatchers.IO) {
            prefs.edit { putBoolean(KEY_APP_LOCK_ENABLED, enabled) }
        }
        _appLockEnabled.emit(enabled)
    }

    override fun getBiometricEnabled(): Flow<Boolean> = _biometricEnabled.asStateFlow()

    override suspend fun setBiometricEnabled(enabled: Boolean) {
        withContext(Dispatchers.IO) {
            prefs.edit { putBoolean(KEY_BIOMETRIC_ENABLED, enabled) }
        }
        _biometricEnabled.emit(enabled)
    }

    override suspend fun getPinHash(): String? {
        return withContext(Dispatchers.IO) {
            prefs.getString(KEY_PIN_HASH, null)
        }
    }

    override suspend fun setPinHash(hash: String?) {
        withContext(Dispatchers.IO) {
            prefs.edit { putString(KEY_PIN_HASH, hash) }
        }
    }
}