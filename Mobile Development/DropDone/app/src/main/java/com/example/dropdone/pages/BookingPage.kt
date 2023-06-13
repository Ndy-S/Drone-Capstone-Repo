package com.example.dropdone.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.R
import com.example.dropdone.ui.navigation.Menu
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

@Composable
fun BookingPage(
    laundryId: String,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
) {
    val collection = Firebase.firestore.collection("booking")
    val document = collection.document()

    var type by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    val showWarning = remember { mutableStateOf(false) }
    val isErrorType = type.isEmpty()
    val isErrorWeight = weight.isEmpty()
    var deliveryValue by remember { mutableStateOf(false) }
    var pickValue by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.clothes_type),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = type,
            placeholder = { Text(stringResource(R.string.clothes_input_type)) },
            singleLine = true,
            onValueChange = { newType ->
                type = newType
            },
            isError = isErrorType,
            supportingText = {
                if (isErrorType) {
                    Text(
                        text = stringResource(R.string.field_notEmpty),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.clothes_weight),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = weight,
            placeholder = { Text(stringResource(R.string.clothes_input_weight)) },
            singleLine = true,
            onValueChange = { newWeight ->
                weight = newWeight
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            isError = isErrorWeight,
            supportingText = {
                if (isErrorWeight) {
                    Text(
                        text = stringResource(R.string.field_notEmpty),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.method),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(R.string.delivery),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
        ) {
            Log.d("reviews", "DeliveryOption: $deliveryValue")
            Button(
                onClick = {
                    deliveryValue = false
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(220.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!deliveryValue) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary,
                    contentColor = if (!deliveryValue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = "Antar Sendiri",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }

            Button(
                onClick = {
                    deliveryValue = true
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(220.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (deliveryValue) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary,
                    contentColor = if (deliveryValue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = "Pakai Kurir",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.pickup),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
        ) {
            Log.d("reviews", "DeliveryOption: $pickValue")
            Button(
                onClick = {
                    pickValue = false
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(220.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!pickValue) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary,
                    contentColor = if (!pickValue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = "Antar Sendiri",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }

            Button(
                onClick = {
                    pickValue = true
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(220.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pickValue) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary,
                    contentColor = if (pickValue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = "Pakai Kurir",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.note),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = note,
            onValueChange = { newNote ->
                note = newNote
            },
            modifier = Modifier
                .fillMaxWidth()
                .size(120.dp)
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

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (isErrorType && isErrorWeight) {
                    showWarning.value = true
                } else if (isErrorType) {
                    showWarning.value = true
                } else if (isErrorWeight) {
                    showWarning.value = true
                } else {
                    val bookingData = hashMapOf(
                        "laundry_id" to laundryId,
                        "review_author_id" to "xiHZ4Ky3zZCy4EsX5o9W",
                        "type" to type,
                        "weight" to weight.toInt(),
                        "delivery" to deliveryValue,
                        "delivery_cost" to 1000,
                        "pick" to pickValue,
                        "note" to note
                    )

                    document.set(bookingData)
                        .addOnSuccessListener {
                            val bookingId = document.id
                            Log.d("idid", "BookingPage: $bookingId")
                            navController.navigate(Menu.DetailBooking.bookingId(bookingId))
                        }
                        .addOnFailureListener {
                            Log.d("set", "BookingPage: $bookingData")
                        }
                }
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.booking),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
    }
}