package com.example.dropdone.ui.navigation

const val DETAIL_ARGUMENT_KEY = "id"

sealed class Menu(val route: String) {
    object Home : Menu("home_page")
    object Profile : Menu("profile_page")
    object Order : Menu("order_page")

    object Detail : Menu("detail_page/{$DETAIL_ARGUMENT_KEY}") {
        fun laundryId(id: String = ""): String {
            return "detail_page/$id"
        }
    }

    object Booking : Menu("booking_page/{$DETAIL_ARGUMENT_KEY}") {
        fun laundryId(id: String = ""): String {
            return "booking_page/$id"
        }
    }

    object DetailBooking : Menu("detail_booking_page/{$DETAIL_ARGUMENT_KEY}") {
        fun bookingId(id: String = ""): String {
            return "detail_booking_page/$id"
        }
    }

    object MapRoute : Menu("map_route/{$DETAIL_ARGUMENT_KEY}") {
        fun laundryId(id: String = ""): String {
            return "map_route/$id"
        }
    }
}