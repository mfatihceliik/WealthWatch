package com.example.wealthwatch.presentation.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable data object Portfolio : Route
    @Serializable data object Settings : Route
    @Serializable data object SettingsTheme : Route
    @Serializable data object SettingsLanguage : Route
    @Serializable data object SettingsEditProfile : Route
    @Serializable data object SettingsSecurity : Route
    @Serializable data object SettingsNotification : Route
    @Serializable data object Watchlist : Route

    @Serializable data object Market : Route

    // Eğer bir ekrana veri göndermek istersen şöyle yapıyorsun:
    // @Serializable data class AssetDetail(val assetId: Int) : Route()
    @Serializable data class AssetDetail(val symbol: String, val typeCode: String) : Route
    @Serializable data object Search : Route
    @Serializable data object SearchAsset : Route
}
