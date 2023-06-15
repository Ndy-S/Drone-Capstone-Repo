package com.example.dropdone.ui.components.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.dropdone.model.SideMenuItem
import com.example.dropdone.model.UserData

@Composable
fun SideMenuHeader(
    userData: UserData?,
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .height(150.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (userData != null) {
                Image(
                    painter = rememberAsyncImagePainter(userData.profilePictureUrl),
                    contentDescription = "Profile User",
                    modifier = Modifier
                        .size(75.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(10.dp))
                userData.username?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                userData.email?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun SideMenuBody(
    onSignOut: () -> Unit,
    items: List<SideMenuItem>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item.id)
                    }
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
    Button(
        onClick = onSignOut,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "Sign out",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}