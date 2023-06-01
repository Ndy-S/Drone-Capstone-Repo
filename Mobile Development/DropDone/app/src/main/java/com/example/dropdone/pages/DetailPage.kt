package com.example.dropdone.pages

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.data.DataLaundryDummy
import com.example.dropdone.data.ObjectLaundryDummy

@Composable
fun DetailPage(
    laundryId: String,
    navController: NavController = rememberNavController()
) {
    val laundry = getLaundryData(laundryId)
    Text(
        text = laundry.title
    )
}

fun getLaundryData(id: String): DataLaundryDummy {
    return ObjectLaundryDummy.obLaundry.find { it.id == id } ?: throw IllegalArgumentException("Invalid Laundry Id")
}