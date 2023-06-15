package com.example.dropdone.ui.components.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.dropdone.model.UserData
import com.example.dropdone.ui.navigation.Menu
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

@Composable
fun DetailBookingContent(
    bookingId: String,
    client: OkHttpClient,
    userData: UserData?,
    navController: NavController = rememberNavController()
) {
    val collection = Firebase.firestore.collection("reviews")
    val document = collection.document()

    val dataBooking = getDataBooking(bookingId)
    var rate by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    val isErrorRate = rate.isEmpty()
    val isErrorComment = comment.isEmpty()
    val showWarning = remember { mutableStateOf(false) }

    val predictionState = remember { mutableStateOf(0.0) }

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
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = laundry.laundry_name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.detail_booking),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.clothes_type),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = booking.type,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.clothes_weight),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = booking.weight.toString() + "Kg",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.delivery),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Log.d("booking", "Delivery: ${booking.delivery}")
                        Text(
                            text = if (booking.delivery) {
                                stringResource(R.string.using_courier)
                            } else {
                                stringResource(R.string.self_deliver)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.pickup),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Log.d("booking", "Pick: ${booking.pick}")
                        Text(
                            text = if (booking.pick) {
                                stringResource(R.string.using_courier)
                            } else {
                                stringResource(R.string.self_deliver)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.laundry_price),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Rp" + (laundry.price.toInt().times(booking.weight)),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.delivery_price),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Rp" + (booking.delivery_cost),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface,
                        thickness = 2.dp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.total_price),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Rp" + ((laundry.price.toInt()
                                .times(booking.weight)) + booking.delivery_cost),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.rate_comment),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                OutlinedTextField(
                    value = rate,
                    placeholder = { Text(stringResource(R.string.rate)) },
                    singleLine = true,
                    onValueChange = { newRate ->
                        rate = newRate
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    isError = isErrorRate,
                    supportingText = {
                        if (isErrorRate) {
                            Text(
                                text = stringResource(R.string.field_notEmpty),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.description),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                OutlinedTextField(
                    value = comment,
                    singleLine = true,
                    onValueChange = { newComment ->
                        comment = newComment
                    },
                    isError = isErrorComment,
                    supportingText = {
                        if (isErrorComment) {
                            Text(
                                text = stringResource(R.string.field_notEmpty),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp)
                )

                if (showWarning.value) {
                    AlertDialog(
                        onDismissRequest = { showWarning.value = false },
                        title = { Text("Warning") },
                        text = {
                            Text(
                                text = stringResource(R.string.warning),
                                color = Color.Red
                            )
                        },
                        confirmButton = {
                            Button(onClick = { showWarning.value = false }) {
                                Text(
                                    text = stringResource(R.string.confirm),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (isErrorRate && isErrorComment) {
                            showWarning.value = true
                        } else if (isErrorRate) {
                            showWarning.value = true
                        } else if (isErrorComment) {
                            showWarning.value = true
                        } else {
                            predictSentiment(comment, client) { prediction ->
                                predictionState.value = prediction
                            }

                            predictSentiment(comment, client) { prediction ->
                                val detailBookingData = hashMapOf(
                                    "laundry_id" to booking.laundry_id,
                                    "review_author_id" to userData?.userId,
                                    "review_rating" to rate,
                                    "review_text" to comment,
                                    "sentiment_score" to prediction,
                                )

                                document.set(detailBookingData)
                                    .addOnSuccessListener {
                                        navController.navigate(Menu.Detail.laundryId(booking.laundry_id))
                                    }
                                    .addOnFailureListener {
                                        Log.d("set", "BookingPage: $detailBookingData")
                                    }
                            }

                        }
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier
                        .fillMaxWidth()
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

private fun predictSentiment(
    text: String,
    client: OkHttpClient,
    callback: (Double) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            if (text.isBlank()) {
                return@launch
            }

            val requestBody = FormBody.Builder()
                .add("text", text)
                .build()

            val request = Request.Builder()
                .url("https://sentiment-score-3mhxb4ae4a-et.a.run.app/")
                .post(requestBody)
                .build()

            Log.d("Hasil", request.toString())

            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }
            Log.d("Hasil", response.toString())

            val jsonStr = response.body?.string()
            Log.d("Hasils", jsonStr.toString())

            val prediction = withContext(Dispatchers.Main) {
                val json = jsonStr?.let { JSONObject(it) }
                Log.d("Hasil", json.toString())
                json?.getDouble("sentiment_score")
            }
            Log.d("Hasil", prediction.toString())

            if (prediction != null) {
                withContext(Dispatchers.Main) {
                    callback(prediction)
                }
            }
        } catch (e: IOException) {
            Log.d("Hasil", "IOException", e)
        } catch (e: Exception) {
            Log.d("Hasil", "Exception", e)
        }
    }
}

