package com.example.wealthwatch.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.wealthwatch.presentation.asset_detail.AssetDetailScreen
import com.example.wealthwatch.presentation.market.MarketScreen
import com.example.wealthwatch.presentation.navigation.routes.Route
import com.example.wealthwatch.presentation.portfolio.PortfolioScreen
import com.example.wealthwatch.presentation.search.SearchScreen
import com.example.wealthwatch.presentation.settings.SettingsScreen
import com.example.wealthwatch.presentation.settings.edit_profile.EditProfileScreen
import com.example.wealthwatch.presentation.settings.language.LanguageScreen
import com.example.wealthwatch.presentation.settings.notifications.NotificationsScreen
import com.example.wealthwatch.presentation.settings.security.SecurityScreen
import com.example.wealthwatch.presentation.settings.theme.ThemeScreen
import com.example.wealthwatch.presentation.watchlist.WatchlistScreen
import com.example.wealthwatch.presentation.search_asset.SearchAssetScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.Market, enterTransition = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
        )
    }, exitTransition = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
        )
    }, popEnterTransition = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
        )
    }, popExitTransition = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
        )
    }) {
        composable<Route.Portfolio> {
            PortfolioScreen(
                onNavigateToDetail = { symbol, type ->
                    navController.navigate(Route.AssetDetail(symbol, type.code))
                })
        }
        composable<Route.Market> {
            MarketScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable<Route.SearchAsset> {
            SearchAssetScreen(
                onNavigateToDetail = { symbol, type ->
                    navController.navigate(Route.AssetDetail(symbol, type.code))
                })
        }
        composable<Route.Settings> {
            SettingsScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable<Route.SettingsTheme> {
            ThemeScreen()
        }
        composable<Route.SettingsLanguage> {
            LanguageScreen()
        }
        composable<Route.SettingsEditProfile> {
            EditProfileScreen()
        }
        composable<Route.SettingsSecurity> {
            SecurityScreen()
        }
        composable<Route.SettingsNotification> {
            NotificationsScreen()
        }
        composable<Route.Search> {
            SearchScreen(onNavigateToDetail = { symbol, type ->
                navController.navigate(Route.AssetDetail(symbol, type.code))
            }, onNavigateUp = {
                navController.navigateUp()
            })
        }

        composable<Route.AssetDetail> { backStackEntry ->
            val route: Route.AssetDetail = backStackEntry.toRoute()
            AssetDetailScreen(
                symbol = route.symbol,
            )
        }
    }
}
