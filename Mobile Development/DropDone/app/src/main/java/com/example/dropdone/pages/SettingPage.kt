package com.example.dropdone.pages

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.ui.components.SettingContent

@Composable
fun SettingPage(
    navController: NavController = rememberNavController()
) {
    SettingContent()
}