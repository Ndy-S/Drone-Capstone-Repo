package com.example.dropdone

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dropdone.model.UserData
import com.example.dropdone.pages.BookingPage
import com.example.dropdone.pages.DetailBookingPage
import com.example.dropdone.pages.DetailPage
import com.example.dropdone.pages.HomePage
import com.example.dropdone.pages.OrderPage
import com.example.dropdone.pages.ProfilePage
import com.example.dropdone.ui.navigation.DETAIL_ARGUMENT_KEY
import com.example.dropdone.ui.navigation.Menu

@Composable
fun HomeApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Menu.Home.route
            ) {
                composable(Menu.Home.route) {
                    HomePage(navController = navController)
                }
                composable(Menu.Profile.route) {
                    ProfilePage()
                }
                composable(Menu.Order.route) {
                    OrderPage()
                }
                composable(
                    Menu.Detail.route,
                    arguments = listOf(
                        navArgument(DETAIL_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val laundryId = it.arguments?.getString(DETAIL_ARGUMENT_KEY)
                    if (laundryId != null) {
                        DetailPage(
                            laundryId = laundryId,
                            navController = navController
                        )
                    }
                }
                composable(
                    Menu.Booking.route,
                    arguments = listOf(
                        navArgument(DETAIL_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val laundryId = it.arguments?.getString(DETAIL_ARGUMENT_KEY)
                    if (laundryId != null) {
                        BookingPage(
                            laundryId = laundryId,
                            navController = navController
                        )
                    }
                }
                composable(
                    Menu.DetailBooking.route,
                    arguments = listOf(
                        navArgument(DETAIL_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val bookingId = it.arguments?.getString(DETAIL_ARGUMENT_KEY)
                    if (bookingId != null) {
                        DetailBookingPage(
                            bookingId = bookingId,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
