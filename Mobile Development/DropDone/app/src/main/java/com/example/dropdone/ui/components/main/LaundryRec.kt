package com.example.dropdone.ui.components.main

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.dropdone.R
import com.example.dropdone.model.Laundry
import com.example.dropdone.model.UserData
import com.example.dropdone.ui.navigation.Menu
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

@Composable
fun LaundryRec(
    client: OkHttpClient,
    userData: UserData?,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
) {
    val recommendationState = remember { mutableStateListOf<String>() }
    val laundryState = remember { mutableStateListOf<Laundry>() }
    val userId = userData?.userId

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = (stringResource(R.string.recommendation)),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
        )
        getRecommendation(userId = userId, client = client) { recommendation ->
            recommendationState.clear()
            recommendationState.addAll(recommendation)

            getLaundryData(recommendation) { laundry ->
                Log.d("HasilItems", laundry.toString())
                laundryState.clear()
                laundryState.addAll(laundry)
            }
        }

        LazyColumn {
            items(laundryState) { laundry ->
                Card(
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.onSurface),
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
                            .clickable {
                                navController.navigate(Menu.Detail.laundryId(laundry.id))
                            }
                    ) {
                        AsyncImage(
                            model = laundry.icon,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp)
                                .size(50.dp)
                                .clip(RectangleShape)
                        )
                        Column(
                            modifier = Modifier
                                .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                                .width(320.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = laundry.laundry_name,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                Image(
                                    painter = painterResource(R.drawable.star),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .offset(x = 60.dp)
                                )
                                Text(
                                    text = laundry.rating.toString(),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.offset(x = 67.dp)
                                )
                            }
                            Text(
                                text = laundry.address,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 14.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getRecommendation(userId: String?, client: OkHttpClient, callback: (List<String>) -> Unit) {
    val recommendationState = mutableListOf<String>()
    var showError = false

    CoroutineScope(Dispatchers.IO).launch {
        if (userId.isNullOrBlank()) {
            return@launch
        }

        try {
            val requestBody = FormBody.Builder()
                .add("userid", userId)
                .build()

            val request = Request.Builder()
                .url("https://recommendation-laundry-3mhxb4ae4a-et.a.run.app/")
                .post(requestBody)
                .build()

            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }

            val jsonStr = response.body?.string()
            Log.d("HasilJson", jsonStr.toString())

            if (response.isSuccessful && !jsonStr.isNullOrBlank()) {
                val json = JSONObject(jsonStr)
                val error = json.optString("error")

                if (error.isNullOrEmpty()) {
                    val predictionArray = json.optJSONArray("recommendation")

                    withContext(Dispatchers.Main) {
                        recommendationState.clear()
                        if (predictionArray != null) {
                            for (i in 0 until predictionArray.length()) {
                                val prediction = predictionArray.getString(i)
                                recommendationState.add(prediction)
                            }
                        }
                    }
                } else {
                    showError = true
                    Log.d("Hasil", "API Error: $error")
                }
            } else {
                showError = true
                Log.d("Hasil", "API Error: ${response.code}")
            }
        } catch (e: IOException) {
            showError = true
            Log.d("Hasil", "IOException", e)
        } catch (e: Exception) {
            showError = true
            Log.d("Hasil", "Exception", e)
        }

        withContext(Dispatchers.Main) {
            if (showError) {
                // Handle error case
                callback(emptyList())
            } else {
                // Invoke the callback with recommendationState
                callback(recommendationState)
            }
        }
    }
}

private fun getLaundryData(recommendation: List<String>, callback: (List<Laundry>) -> Unit) {
    val db = Firebase.firestore
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val laundryData = mutableListOf<Laundry>()
            val collection = db.collection("laundry")

            val snapshot = collection.get().await()

            for (document in snapshot.documents) {
                val data = document.data
                val laundryId = document.id

                if (data != null && recommendation.contains(laundryId)) {
                    val documentData = Laundry(
                        id = laundryId,
                        laundry_name = data["laundry_name"].toString(),
                        address = data["address"].toString(),
                        email = data["email"].toString(),
                        icon = data["icon"].toString(),
                        latitude = data["latitude"].toString(),
                        longitude = data["longitude"].toString(),
                        opening_hours = data["opening_hours"].toString(),
                        phone_number = data["phone_number"].toString(),
                        price = data["price"].toString().toString(),
                        rating = data["rating"].toString().toDouble()
                    )
                    laundryData.add(documentData)
                }
            }

            withContext(Dispatchers.Main) {
                callback(laundryData)
            }
        } catch (e: Exception) {
            Log.e("Firestore Error", "Error getting laundry documents: ", e)
        }
    }
}