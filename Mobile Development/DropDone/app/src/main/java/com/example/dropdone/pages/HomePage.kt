package com.example.dropdone.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.R
import com.example.dropdone.model.SideMenuItem
import com.example.dropdone.ui.components.home.*
import com.example.dropdone.ui.navigation.Menu
import kotlinx.coroutines.launch


@Composable
fun HomePage(
    navController: NavController = rememberNavController()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                SideMenuHeader()
                SideMenuBody(
                    items = listOf(
                        SideMenuItem(
                            id = 1,
                            title = stringResource(R.string.home),
                            icon = Icons.Default.Home
                        ),
                        SideMenuItem(
                            id = 2,
                            title = stringResource(R.string.profile),
                            icon = Icons.Default.AccountCircle
                        ), SideMenuItem(
                            id = 3,
                            title = stringResource(R.string.order_list),
                            icon = Icons.Default.Info
                        ), SideMenuItem(
                            id = 4,
                            title = stringResource(R.string.setting),
                            icon = Icons.Default.Settings
                        )
                    ),
                    onItemClick = { itemId ->
                        scope.launch {
                            when (itemId) {
                                1 -> navController.navigate(Menu.Home.route)
                                2 -> navController.navigate(Menu.Profile.route)
                                3 -> navController.navigate(Menu.Order.route)
                                4 -> navController.navigate(Menu.Setting.route)
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
                Box(modifier = Modifier) {
                    Image(
                        painter = painterResource(R.drawable.gray),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.height(190.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Profile()
                        SearchBarLaundry(navController = navController)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "") },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    )
}