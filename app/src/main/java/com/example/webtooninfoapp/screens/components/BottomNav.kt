package com.example.webtooninfoapp.screens.components

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.webtooninfoapp.data.models.MangaDTO
import com.example.webtooninfoapp.navigation.Routes
import com.example.webtooninfoapp.screens.Details
import com.example.webtooninfoapp.screens.Favourite
import com.example.webtooninfoapp.screens.Home
import com.google.gson.Gson

@Composable
fun BottomNav() {
    val navController1 = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController1) }
    ) { innerPadding ->
        NavHost(
            navController = navController1,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Routes.Home.route) {
                Home(navController = navController1)
            }
            composable(Routes.Favourites.route) {
                Favourite(navController = navController1)
            }


            composable(
                route = "${Routes.Details.route}/{toon}",
                arguments = listOf(navArgument("toon") { type = NavType.StringType })
            ) { backStackEntry ->
                val webtoonJson = backStackEntry.arguments?.getString("toon")
                val decodedJson = Uri.decode(webtoonJson) // Decode the JSON string
                val webtoon = Gson().fromJson(decodedJson, MangaDTO::class.java)
                Details(navController = navController1, webtoon = webtoon)
            }
        }
    }
}

