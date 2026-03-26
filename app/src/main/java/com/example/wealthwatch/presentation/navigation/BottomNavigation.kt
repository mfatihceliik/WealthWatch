package com.example.wealthwatch.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import com.example.wealthwatch.presentation.components.WWIcon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.navigation.routes.Route
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.LocalSpacing

private val BottomNavItems = listOf(
    BottomNavItem(Route.Market, Icons.Default.Home, R.string.market),
    BottomNavItem(Route.SearchAsset, Icons.Default.Search, R.string.search),
    BottomNavItem(Route.Portfolio, Icons.Default.PieChart, R.string.nav_portfolio),
    BottomNavItem(Route.Settings, Icons.Default.Settings, R.string.nav_settings),
)
@Composable
fun WealthBottomNavigation(
    currentDestination: NavDestination?,
    onNavigate: (Route) -> Unit
) {
    NavigationBar(
        containerColor = AppTheme.colors.surface,
        tonalElevation = LocalSpacing.current.cardElevation
    ) {
        BottomNavItems.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
            NavigationBarItem(
                icon = { WWIcon(imageVector = item.icon) },
                label = { WWText(stringResource(item.titleResId)) },
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = AppTheme.colors.primary,
                    selectedIconColor = AppTheme.colors.onPrimary,
                    selectedTextColor = AppTheme.colors.text,
                )
            )
        }
    }
}

@Immutable
data class BottomNavItem(
    val route: Route,
    val icon: ImageVector,
    @param:StringRes val titleResId: Int
)
