package com.example.dropdone.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.dropdone.model.Booking
import com.example.dropdone.model.Laundry

@Composable
fun DetailBookingPage(
    bookingId: String,
    navController: NavController = rememberNavController()
) {
    val dataBooking = getDataBooking(bookingId)
    if (dataBooking.isEmpty()) {
        Text(text = "")
    } else {
        val booking = dataBooking.first()

        val dataLaundry = getDataLaundry(booking.laundry_id)
        if (dataLaundry.isEmpty()) {
            Text(text = "")
        } else {
            val laundry = dataLaundry.first()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = laundry.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp)
                )
                Text(
                    text = laundry.laundry_name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.detail_booking),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.clothes_type),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = booking.type,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun getDataBooking(
    id: String,
    mainViewModel: MainViewModel = viewModel(factory = ViewModelFactory(LaundryRepository()))
): List<Booking> {
    val bookingList by produceState<List<Booking>>(initialValue = emptyList()) {
        val data = mutableListOf<Booking>()
        val bookingData = mainViewModel.getBookingFromRepo()

        for (document in bookingData) {
            data.add(document)
        }

        value = data.filter { it.id == id }

        if (value.isEmpty()) {
            throw IllegalArgumentException("Invalid Laundry Id")
        }
    }
    return bookingList
}

@Composable
fun getDataLaundry(
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