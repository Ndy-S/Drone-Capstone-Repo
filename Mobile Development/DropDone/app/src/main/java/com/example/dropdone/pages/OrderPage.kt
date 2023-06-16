package com.example.dropdone.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.dropdone.R
import com.example.dropdone.model.SideMenuItem
import com.example.dropdone.model.SideMenuObject
import com.example.dropdone.model.UserData
import com.example.dropdone.ui.components.AppBar
import com.example.dropdone.ui.components.main.SideMenuBody
import com.example.dropdone.ui.components.main.SideMenuHeader
import com.example.dropdone.ui.components.side.OrderContent
import com.example.dropdone.ui.navigation.Menu
import kotlinx.coroutines.launch

@Composable
fun OrderPage(
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
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppBar(onNavigationIconClick = {
                    scope.launch { drawerState.open() }
                })
                OrderContent(userData)
            }
        }
    )
}