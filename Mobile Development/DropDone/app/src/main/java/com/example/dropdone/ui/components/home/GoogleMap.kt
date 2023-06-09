package com.example.dropdone.ui.components.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropdone.R
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
                .padding(start = 16.dp, top =40.dp, bottom = 8.dp)
        )
        GMap()
    }
}

@Composable
fun GMap() {
    val indonesia = LatLng(0.533758, 101.445226)
    val camPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(indonesia, 20f)
    }
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        GoogleMap(
            modifier = Modifier
                .aspectRatio(1.5f),
            cameraPositionState = camPositionState
        ) {
            Marker(
                state = MarkerState(position = indonesia),
                title = "Indonesia",
                snippet = "Marker in Indonesia"
            )
        }
    }
}