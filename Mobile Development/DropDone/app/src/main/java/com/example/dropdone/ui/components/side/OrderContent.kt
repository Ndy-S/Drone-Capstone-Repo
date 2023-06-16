package com.example.dropdone.ui.components.side

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.dropdone.MainViewModel
import com.example.dropdone.ViewModelFactory
import com.example.dropdone.data.LaundryRepository
import com.example.dropdone.model.Booking
import com.example.dropdone.model.Laundry
import com.example.dropdone.model.UserData

@Composable
fun OrderContent(
    userData: UserData?,
    navController: NavController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel(factory = ViewModelFactory(LaundryRepository()))
) {
    val orderColl = remember { mutableStateListOf<Booking>() }
    val laundryColl = remember { mutableStateListOf<Laundry>() }

    LaunchedEffect(Unit) {
        val orderData = mainViewModel.getBookingFromRepo()
        for (order in orderData) {
            orderColl.add(order)
        }
    }

    LaunchedEffect(Unit) {
        val laundryData = mainViewModel.getLaundryFromRepo()
        for (laundry in laundryData) {
            laundryColl.add(laundry)
        }
    }

    LazyColumn {
        items(1) {
            for (order in orderColl) {
                for (laundry in laundryColl) {
                    if (laundry.id == order.laundry_id) {
                        if (order.review_author_id == userData?.userId) {
                            Card(
                                border = BorderStroke(2.dp, Color.LightGray),
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                        .size(100.dp)
                                ) {
                                    AsyncImage(
                                        model = laundry.icon,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(start = 8.dp, end = 8.dp)
                                            .size(75.dp)
                                            .clip(RectangleShape)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = laundry.laundry_name,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = order.type,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 16.sp,
                                        )
                                        Text(
                                            text = "Rp" + ((laundry.price.toInt()
                                                .times(order.weight)) + order.delivery_cost),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 14.sp,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}