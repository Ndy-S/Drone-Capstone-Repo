package com.example.dropdone.ui.navigation

const val DETAIL_ARGUMENT_KEY = "id"
sealed class Menu(val route: String) {
    object Home: Menu("home_page")
    object Profile: Menu("profile_page")
    object Order: Menu("order_page")
    object Setting: Menu("setting_page")
    object Detail: Menu("detail_page/{$DETAIL_ARGUMENT_KEY}") {
        fun laundryId(id: String = ""): String {
            return "detail_page/$id"
        }
    }
}