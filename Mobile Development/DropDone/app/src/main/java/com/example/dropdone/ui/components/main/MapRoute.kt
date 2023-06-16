package com.example.dropdone.ui.components.main

import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dropdone.MainViewModel
import com.example.dropdone.ViewModelFactory
import com.example.dropdone.data.LaundryRepository
import com.example.dropdone.model.Laundry
import com.example.dropdone.model.UserData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.OkHttpClient

@Composable
fun MapRoute(
    laundryId: String,
    userData: UserData?,
    client: OkHttpClient
) {
    val db = FirebaseFirestore.getInstance()
    val collection = db.collection("users")
    val query = collection.whereEqualTo("email", userData?.email)
    val laundryRoute = getLaundryDataRoute(laundryId)

    if (laundryRoute.isEmpty()) {
        Text(text = "")
    } else {
        val laundry = laundryRoute.first()

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val mapView = MapView(context).apply {
                    onCreate(null)
                    onResume()
                }

                mapView.getMapAsync { map ->
                    map.uiSettings.isZoomControlsEnabled = true
                    query.get()
                        .addOnSuccessListener { querySnapshot ->
                            val firstDocument = querySnapshot.documents.firstOrNull()
                            if (firstDocument != null) {
                                val document = firstDocument.getString("address")
                                if (!document.isNullOrEmpty()) {
                                    val geoCoder = Geocoder(context)
                                    val location = geoCoder.getFromLocationName(
                                        "Jl. Kiara Sari Raya 32-28, Margasari, Kec. Buahbatu, Kota Bandung, Jawa Barat 40286",
                                        1
                                    )
                                    if (!location.isNullOrEmpty()) {
                                        val startLatLng = LatLng(location[0].latitude, location[0].longitude)
                                        val endLatLng = LatLng(
                                            laundry.latitude.toDoubleOrNull() ?: 0.0,
                                            laundry.longitude.toDoubleOrNull() ?: 0.0
                                        )

                                        val boundsBuilder = LatLngBounds.builder()
                                        boundsBuilder.include(startLatLng)
                                        boundsBuilder.include(endLatLng)
                                        val bounds = boundsBuilder.build()

                                        val padding = 100 // Adjust this value as needed
                                        val cameraUpdate =
                                            CameraUpdateFactory.newLatLngBounds(bounds, padding)
                                        map.moveCamera(cameraUpdate)

                                        val color = Color.Blue
                                        val polylineOptions = PolylineOptions()
                                            .add(startLatLng)
                                            .add(endLatLng)
                                            .color(color.toArgb())
                                            .width(10f)
                                        map.addPolyline(polylineOptions)

                                        val startMarkerOptions = MarkerOptions()
                                            .position(startLatLng)
                                            .title("Start Location")

                                        val endMarkerOptions = MarkerOptions()
                                            .position(endLatLng)
                                            .title(laundry.laundry_name)

                                        map.addMarker(startMarkerOptions)
                                        map.addMarker(endMarkerOptions)
                                    } else {
                                        Log.e("Map", "Location list is empty")
                                        Log.e("Map", "Geocoding results: $location")
                                    }
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Firestore", "Error retrieving data: ", exception)
                        }
                }
                mapView
            }
        )
    }
}

@Composable
fun getLaundryDataRoute(
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