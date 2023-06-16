package com.example.dropdone.ui.components.side

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropdone.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.dropdone.model.UserData
import com.example.dropdone.ui.navigation.Menu
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun ProfileHeader(
    userData: UserData?,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userData != null) {
            Image(
                painter = rememberAsyncImagePainter(userData.profilePictureUrl),
                contentDescription = "Profile User",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            userData.username?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .paddingFromBaseline(32.dp, 16.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileContent(
    userData: UserData?,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    var userName by remember { mutableStateOf(userData?.username) }
    var userEmail by remember { mutableStateOf(userData?.email) }
    var userAddress by remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()
    val collection = db.collection("users")
    val query = collection.whereEqualTo("email", userData?.email)

    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(stringResource(R.string.username))
        OutlinedTextField(
            value = userName.toString(),
            enabled = false,
            onValueChange = { newUserName ->
                userName = newUserName
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        Text(stringResource(R.string.email))
        OutlinedTextField(
            value = userEmail.toString(),
            enabled = false,
            onValueChange = { newUserEmail ->
                userEmail = newUserEmail
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        Text(stringResource(R.string.current_loc))
        OutlinedTextField(
            value = userAddress,
            onValueChange = { newUserAddress ->
                userAddress = newUserAddress
            },
            label = { Text("Jl. Nama jalan, kelurahan, kota, provinsi") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
        )

        Button(
            onClick = {
                query.get()
                    .addOnSuccessListener { querySnapshot ->
                        val firstDocument = querySnapshot.documents.firstOrNull()
                        if (firstDocument != null) {
                            val document = firstDocument.reference

                            val userLocation = hashMapOf(
                                "address" to userAddress
                            )

                            document.update(userLocation as Map<String, Any>)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Update successful!", Toast.LENGTH_SHORT).show()
                                    navController.navigate(Menu.Home.route)
                                }
                                .addOnFailureListener { e ->
                                    Log.d("set", "BookingPage: ", e)
                                }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.d("profileHeader", e.toString())
                    }
            },
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                stringResource(R.string.profile_update),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )
        }
    }
}