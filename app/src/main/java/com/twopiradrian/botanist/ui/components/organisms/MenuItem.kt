package com.twopiradrian.botanist.ui.components.organisms

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.core.navigation.AppScreens

sealed class MenuItem(val route: String, val icon: Int, val label: Int ) {
    companion object {
        val items = listOf(Explore, Write, Profile)
    }

    data object Explore: MenuItem(
        AppScreens.Explore.route,
        R.drawable.ic_search,
        R.string.nav_explore,
    )

    data object Write: MenuItem(
        AppScreens.Write.route,
        R.drawable.ic_write,
        R.string.nav_write,
    )

    data object Profile: MenuItem(
        AppScreens.Profile.route,
        R.drawable.ic_account,
        R.string.nav_profile
    )

}

@Composable
fun AppNavigationBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .height(56.dp),
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        MenuItem.items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = null,
                        tint = if (currentRoute == item.route) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                    disabledIconColor = MaterialTheme.colorScheme.onPrimary,
                    disabledTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    selectedIconColor = MaterialTheme.colorScheme.onBackground,
                    selectedTextColor = MaterialTheme.colorScheme.onBackground,
                )
            )
        }
    }
}

@Composable
fun AppNavigationRail(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationRail(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        MenuItem.items.forEach { item ->
            NavigationRailItem(
                selected = currentRoute == item.route,
                colors = NavigationRailItemColors(
                    selectedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                    disabledIconColor = MaterialTheme.colorScheme.onPrimary,
                    disabledTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    selectedIconColor = MaterialTheme.colorScheme.onBackground,
                    selectedTextColor = MaterialTheme.colorScheme.onBackground,
                ),
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = null,
                        tint = if (currentRoute == item.route) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary
                    )
                },

            )
        }
    }
}

@Composable
fun AppPermanentNavigation(
    navController: NavController,
    content: @Composable () -> Unit
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.padding(vertical = 12.dp).width(240.dp).clip(RoundedCornerShape(12.dp)),
                drawerContainerColor = MaterialTheme.colorScheme.primary,
            ) {
                MenuItem.items.forEach { item ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(12.dp),
                        label = {
                            Text(stringResource(id = item.label))
                                },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            selectedIconColor = MaterialTheme.colorScheme.onBackground,
                            selectedTextColor = MaterialTheme.colorScheme.onBackground,
                            unselectedContainerColor = MaterialTheme.colorScheme.primary,
                            unselectedBadgeColor = MaterialTheme.colorScheme.onPrimary,
                            selectedContainerColor = MaterialTheme.colorScheme.onPrimary,
                            selectedBadgeColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        icon = {
                            Icon(
                                painterResource(id = item.icon),
                                contentDescription = null,
                                tint = if (currentRoute == item.route) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary
                            )
                        },
                    )
                }
            }
        }
    ) {
        content()
    }
}