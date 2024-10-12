package com.example.webtooninfoapp.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.webtooninfoapp.navigation.Routes

@Composable
fun BottomBar(navController: NavHostController) {

    val backStackEntry = navController.currentBackStackEntryAsState()
    val list = listOf(
        BottomNavItem("Home", Routes.Home.route, Icons.Rounded.Home),
        BottomNavItem("Details", Routes.Favourites.route, Icons.Rounded.Favorite)
    )
    BottomAppBar {
        list.forEach {
            val selected = it.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick =
                {
                    navController.navigate(it.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id)
                        {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = it.icon, contentDescription = it.title) }
            )
        }
    }
}
