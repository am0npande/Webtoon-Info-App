package com.example.webtooninfoapp.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Details : Routes("details")
    object Favourites : Routes("favourites")
    object BottomBarNav :Routes("bottomBar")

}