package com.example.dropdone.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dropdone.R
import com.example.dropdone.data.SideMenuItem

@Composable
fun SideMenuHeader() {
    Box(

    ) {
        Image(
            painter = painterResource(R.drawable.gray),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.height(150.dp)
        )
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "Profile User",
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = stringResource(R.string.username))
            Text(text = stringResource(R.string.emailDummy))
        }
    }
}

@Composable
fun SideMenuBody(
    items: List<SideMenuItem>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(modifier) {
        items(items) {item ->
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
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.title
                )
            }
        }
    }
}