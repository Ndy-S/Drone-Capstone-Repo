package com.example.dropdone

import androidx.compose.runtime.Composable
import com.example.dropdone.model.UserData
import okhttp3.OkHttpClient

@Composable
fun Validation(
    client: OkHttpClient,
    userData: UserData?,
    onSignOut: () -> Unit
) {
    HomeApp(client, userData, onSignOut)
}