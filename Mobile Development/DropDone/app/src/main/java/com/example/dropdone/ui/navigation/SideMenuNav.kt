package com.example.dropdone.ui.navigation

sealed class Menu(val route: String) {
    object Home: Menu("home_page")
    object Setting: Menu("setting_page")
}