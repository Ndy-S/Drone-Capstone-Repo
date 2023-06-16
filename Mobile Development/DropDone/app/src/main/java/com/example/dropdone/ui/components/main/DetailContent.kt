package com.example.dropdone.ui.components.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.dropdone.MainViewModel
import com.example.dropdone.R
import com.example.dropdone.ViewModelFactory
import com.example.dropdone.data.LaundryRepository
import com.example.dropdone.model.Laundry
import com.example.dropdone.model.Reviews
import com.example.dropdone.ui.navigation.Menu
import okhttp3.OkHttpClient

@Composable
fun DetailContent(
    laundryId: String,
    navController: NavController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel(factory = ViewModelFactory(LaundryRepository()))
) {
    val laundryListDetail = getLaundryDataDetail(laundryId)
    val reviewsColl = remember { mutableStateListOf<Reviews>() }

    LaunchedEffect(Unit) {
        val reviewsData = mainViewModel.getReviewsFromRepo()
        for (document in reviewsData) {
            reviewsColl.add(document)
        }
    }

    if (laundryListDetail.isEmpty()) {
        Text(text = "")
    } else {
        val laundry = laundryListDetail.first()
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            AsyncImage(
                model = laundry.icon,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = laundry.laundry_name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = laundry.rating.toString(),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = laundry.address,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.opening_hours),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = laundry.opening_hours,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.price),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = laundry.price,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.reviewer_comment),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(8.dp)
            )
            Card(
                border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
                    .size(250.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                LazyColumn {
                    items(1) {
                        for (document in reviewsColl) {
                            if (laundry.id == document.laundry_id) {
                                Divider(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    thickness = 2.dp
                                )
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.star),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(end = 4.dp)
                                        )
                                        Text(
                                            text = document.review_rating.toString(),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = document.review_text,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 14.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Button(
                    onClick = {
                              navController.navigate(Menu.MapRoute.laundryId(laundryId))
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier.width(220.dp)
                ) {
                    Text(
                        text = stringResource(R.string.route),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(Menu.Booking.laundryId(laundryId))
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier.width(220.dp)
                ) {
                    Text(
                        text = stringResource(R.string.booking),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun getLaundryDataDetail(
    id: String,
    mainViewModel: MainViewModel = viewModel(factory = ViewModelFactory(LaundryRepository()))
): List<Laundry> {
    val laundryList by produceState<List<Laundry>>(initialValue = emptyList()) {
        val data = mutableListOf<Laundry>()
        val laundryData = mainViewModel.getLaundryFromRepo()

        for (document in laundryData) {
            data.add(document)
        }

        value = data.filter { it.id == id }

        if (value.isEmpty()) {
            throw IllegalArgumentException("Invalid Laundry Id")
        }
    }
    return laundryList
}