package com.example.dropdone.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.model.SideMenuObject
import com.example.dropdone.model.UserData
import com.example.dropdone.ui.components.AppBar
import com.example.dropdone.ui.components.main.BookingContent
import com.example.dropdone.ui.components.main.SideMenuBody
import com.example.dropdone.ui.components.main.SideMenuHeader
import com.example.dropdone.ui.navigation.Menu
import kotlinx.coroutines.launch

@Composable
fun BookingPage(
    laundryId: String,
    userData: UserData?,
    onSignOut: () -> Unit,
    navController: NavController = rememberNavController()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val sideMenuContent = remember { SideMenuObject.sideMenuContent }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                SideMenuHeader(userData)
                SideMenuBody(
                    onSignOut,
                    items = sideMenuContent,
                    onItemClick = { itemId ->
                        scope.launch {
                            when (itemId) {
                                1 -> navController.navigate(Menu.Home.route)
                                2 -> navController.navigate(Menu.Profile.route)
                                3 -> navController.navigate(Menu.Order.route)
                            }
                            drawerState.close()
                        }
                    }
                )
            }
        },
        gesturesEnabled = drawerState.isOpen,
        content = {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                AppBar(onNavigationIconClick = {
                    scope.launch { drawerState.open() }
                })
                BookingContent(laundryId = laundryId, userData = userData, navController = navController)
            }
        }
    )
}