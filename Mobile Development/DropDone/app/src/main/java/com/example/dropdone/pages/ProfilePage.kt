package com.example.dropdone.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.R
import com.example.dropdone.ui.components.profile.ProfileContent
import com.example.dropdone.ui.components.profile.ProfileHeader

@Composable
fun ProfilePage(
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier) {
            Image(
                painter = painterResource(R.drawable.gray),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(200.dp)
            )
            ProfileHeader()
        }
        ProfileContent()
    }
}