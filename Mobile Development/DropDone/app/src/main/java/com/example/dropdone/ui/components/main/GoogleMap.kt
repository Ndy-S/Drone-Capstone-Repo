package com.example.dropdone.ui.components.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dropdone.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GMapView(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.current_loc),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 16.dp, top = 20.dp, bottom = 8.dp)
        )
        LocationPermissions()
    }
}

@Composable
fun LocationPermissions() {
    val context = LocalContext.current
    val userLocation = remember { mutableStateOf<LatLng?>(null) }
    val camPositionState = rememberCameraPositionState()

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, update the location
                getCurrentLocation(context) { latLng ->
                    Log.d("location", "LocationPermissionsHomeApp: Latitude: ${latLng.latitude}, Longitude: ${latLng.longitude}")
                    userLocation.value = latLng
                    camPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted, get the current location
            getCurrentLocation(context) { latLng ->
                Log.d("location", "LocationPermissionsHomeApp: Latitude: ${latLng.latitude}, Longitude: ${latLng.longitude}")
                userLocation.value = latLng
                camPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
            }
        } else {
            // Request location permission
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Card(
        border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.onSurface),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        GoogleMap(
            modifier = Modifier
                .aspectRatio(1.5f),
            cameraPositionState = camPositionState
        ) {
            val location = userLocation.value
            if (location != null) {
                Marker(
                    state = MarkerState(position = location),
                    title = "Current Location",
                    snippet = "Marker at current location"
                )
            }
        }
    }
}

private fun getCurrentLocation(context: Context, callback: (LatLng) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val lng = location.longitude
                val userLatLng = LatLng(lat, lng)
                callback(userLatLng)
            }
        }
        .addOnFailureListener { exception ->
            // Handle location retrieval failure
            exception.printStackTrace()
        }
}
