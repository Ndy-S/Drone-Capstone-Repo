package com.example.dropdone.ui.components.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.data.DataLaundryDummy

@Composable
fun LaundryRec(

) {

}

@Composable
fun LaundryList(
    laundry: DataLaundryDummy,
    modifier: Modifier,
    navController: NavController = rememberNavController()
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
            .padding(4.dp)
    ) {

    }
}