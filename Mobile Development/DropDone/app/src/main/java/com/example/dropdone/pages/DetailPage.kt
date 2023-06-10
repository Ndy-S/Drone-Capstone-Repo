package com.example.dropdone.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.model.DataLaundryDummy
import com.example.dropdone.model.Laundry
import com.example.dropdone.model.ObjectLaundryDummy

@Composable
fun DetailPage(
    laundryId: String,
    navController: NavController = rememberNavController()
) {
    val laundry = getLaundryData(laundryId)
    Text(
        text = laundry.first().laundry_name
    )
}

fun getLaundryData(id: String): List<Laundry> {
    val laundryList = mutableListOf<Laundry>()
    return laundryList.filter { it.id == id }.ifEmpty {
        throw IllegalArgumentException("Invalid Laundry Id")
    }
}