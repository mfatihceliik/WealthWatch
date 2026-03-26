package com.example.wealthwatch.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.navigation.routes.Route
import kotlin.reflect.KClass

@Stable
class NavigationState(
    val navController: NavHostController
) {
    private val currentBackStackEntry by derivedStateOf { navController.currentBackStackEntry }
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    // Başlık mantığını sadeleştirdik
    @Composable
    fun getTitle(): String {
        val destination = currentDestination ?: return stringResource(R.string.app_name)
        val entry = navController.currentBackStackEntryAsState().value

        return when {
            destination.hasRoute(Route.Market::class) -> stringResource(R.string.market)
            destination.hasRoute(Route.Portfolio::class) -> stringResource(R.string.nav_portfolio)
            destination.hasRoute(Route.Settings::class) -> stringResource(R.string.nav_settings)
            destination.hasRoute(Route.SettingsTheme::class) -> stringResource(R.string.settings_theme)
            destination.hasRoute(Route.SettingsLanguage::class) -> stringResource(R.string.settings_language)
            destination.hasRoute(Route.Search::class) -> stringResource(R.string.search_hint)
            destination.hasRoute(Route.Watchlist::class) -> stringResource(R.string.watchlist)

            // Parametreli başlık alımı:
            destination.hasRoute(Route.AssetDetail::class) -> {
                val args = entry?.toRoute<Route.AssetDetail>()
                args?.symbol?.let { stringResource(R.string.asset_detail_title, it) }
                    ?: stringResource(R.string.app_name)
            }

            else -> stringResource(R.string.app_name)
        }
    }

    val shouldShowBottomBar: Boolean
        @Composable get() {
            val destination = currentDestination
            return destination.hasRoute(Route.Market::class) || destination.hasRoute(Route.Watchlist::class) || destination.hasRoute(
                Route.Portfolio::class
            ) || destination.hasRoute(Route.Settings::class) || destination.hasRoute(Route.SearchAsset::class)
        }

    fun navigateToBottomBarRoute(route: Route) {
        val currentDest = navController.currentDestination
        if (currentDest?.hierarchy?.any { it.hasRoute(route::class) } == true) {
            return
        }

        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}

// Helper extension: Generic route kontrolünü kolaylaştırır
private fun NavDestination?.hasRoute(routeClass: KClass<*>): Boolean {
    return this?.hierarchy?.any { it.hasRoute(routeClass) } == true
}

@Composable
fun rememberNavigationState(
    navController: NavHostController = rememberNavController()
): NavigationState {
    return remember(navController) { NavigationState(navController) }
}
