package com.example.wealthwatch.domain.repository.local.settings

import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.model.settings.AppLanguage
import com.example.wealthwatch.domain.model.settings.WWTheme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getTheme(): Flow<WWTheme>
    fun getCurrentTheme(): WWTheme
    suspend fun setTheme(theme: WWTheme)
    fun getLanguage(): Flow<AppLanguage>
    fun getCurrentLanguage(): AppLanguage
    suspend fun setLanguage(language: AppLanguage)
    fun applyAppConfigs()
    fun getCurrency(): Flow<AppCurrency>
    suspend fun setCurrency(currency: AppCurrency)
    fun getExchangeRates(): Flow<Map<String, Double>>
    suspend fun saveExchangeRates(rates: Map<String, Double>)
    suspend fun getLastKnownRates(): Map<String, Double>

    fun getAppLockEnabled(): Flow<Boolean>
    suspend fun setAppLockEnabled(enabled: Boolean)
    fun getBiometricEnabled(): Flow<Boolean>
    suspend fun setBiometricEnabled(enabled: Boolean)
    suspend fun getPinHash(): String?
    suspend fun setPinHash(hash: String?)
}