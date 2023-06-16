package com.example.dropdone.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.MainViewModel
import com.example.dropdone.R
import com.example.dropdone.ViewModelFactory
import com.example.dropdone.data.LaundryRepository
import com.example.dropdone.model.Booking
import com.example.dropdone.model.Laundry
import com.example.dropdone.model.SideMenuItem
import com.example.dropdone.model.SideMenuObject
import com.example.dropdone.model.UserData
import com.example.dropdone.ui.components.AppBar
import com.example.dropdone.ui.components.main.*
import com.example.dropdone.ui.navigation.Menu
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

@Composable
fun HomePage(
    client: OkHttpClient,
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
                Box {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Profile(userData)
                        SearchBarLaundry(client, userData, navController = navController)
                    }
                }
            }
        }
    )
}