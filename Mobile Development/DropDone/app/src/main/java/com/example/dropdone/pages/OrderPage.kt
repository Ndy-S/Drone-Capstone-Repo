package com.example.dropdone.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun OrderPage(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    Box() {
        Text("Ini adalah Order Page")
    }
}