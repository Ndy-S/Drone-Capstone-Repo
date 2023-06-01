package com.example.dropdone.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dropdone.R
import com.example.dropdone.data.SideMenuItem
import com.example.dropdone.ui.components.home.*
import com.example.dropdone.ui.navigation.DETAIL_ARGUMENT_KEY
import com.example.dropdone.ui.navigation.Menu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            SideMenuHeader()
            SideMenuBody(
                items = listOf(
                    SideMenuItem(
                        id = 1,
                        title = "Home",
                        icon = Icons.Default.Home
                    ),
                    SideMenuItem(
                        id = 2,
                        title = "Setting",
                        icon = Icons.Default.Settings
                    ), SideMenuItem(
                        id = 3,
                        title = "Profile",
                        icon = Icons.Default.AccountCircle
                    )
                ),
                onItemClick = { itemId ->
                    coroutineScope.launch {
                        when (itemId) {
                            1 -> navController.navigate(Menu.Home.route)
                            2 -> navController.navigate(Menu.Setting.route)
                            3 -> navController.navigate(Menu.Profile.route)
                        }
                        scaffoldState.drawerState.close()
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Menu.Home.route
            ) {
                composable(Menu.Home.route) {
                    HomeContent(navController = navController)
                }
                composable(Menu.Setting.route) {
                    SettingPage()
                }
                composable(Menu.Profile.route) {
                    ProfilePage()
                }
                composable(Menu.Detail.route,
                    arguments = listOf(
                        navArgument(DETAIL_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val laundryId = it.arguments?.getString(DETAIL_ARGUMENT_KEY)
                    if (laundryId != null) {
                        DetailPage(
                            laundryId = laundryId
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    navController: NavController = rememberNavController()
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Box(modifier = Modifier) {
            Image(
                painter = painterResource(R.drawable.gray),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(200.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Profile()
                SearchBarLaundry()
            }
        }
        GMapView()
        LaundryRec(navController = navController)
    }
}

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
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    )
}