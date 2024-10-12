@file:Suppress("DEPRECATION")

package com.example.webtooninfoapp.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.webtooninfoapp.data.models.MangaDTO
import com.example.webtooninfoapp.screens.Details
import com.example.webtooninfoapp.screens.components.BottomNav
import com.google.gson.Gson

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.BottomBarNav.route
    ) {

        composable(route = Routes.BottomBarNav.route) {
            BottomNav()
        }
        composable(
            route = "${Routes.Details.route}/{toon}",
            arguments = listOf(navArgument("toon") { type = NavType.StringType })
        ) { backStackEntry ->
            val webtoonJson = backStackEntry.arguments?.getString("toon")
            val decodedJson = Uri.decode(webtoonJson) // Decode the JSON string
            val webtoon = Gson().fromJson(decodedJson, MangaDTO::class.java)
            Details(navController = navController, webtoon = webtoon)
        }

    }
}

