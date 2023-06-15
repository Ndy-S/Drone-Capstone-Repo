package com.example.dropdone.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info

object SideMenuObject {
    val sideMenuContent = listOf(
        SideMenuItem(
            id = 1,
            title = "Home",
            icon = Icons.Default.Home
            ),
        SideMenuItem(
            id = 2,
            title = "Profile",
            icon = Icons.Default.AccountCircle
        ), SideMenuItem(
            id = 3,
            title = "Order List",
            icon = Icons.Default.Info
        )
    )
}