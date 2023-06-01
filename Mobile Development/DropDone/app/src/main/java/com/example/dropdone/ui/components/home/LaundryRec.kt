package com.example.dropdone.ui.components.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.R
import com.example.dropdone.data.DataLaundryDummy
import com.example.dropdone.data.ObjectLaundryDummy
import com.example.dropdone.ui.navigation.Menu

@Composable
fun LaundryRec(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    val laundry = remember { ObjectLaundryDummy.obLaundry}
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
        val laundryObj = remember { ObjectLaundryDummy.obLaundry }
        LazyColumn {
            items(laundryObj, key = { it.id }) {
                LaundryList(
                    laundry = it,
                    modifier = Modifier.fillMaxWidth(),
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun LaundryList(
    laundry: DataLaundryDummy,
    modifier: Modifier,
    navController: NavController = rememberNavController()
) {
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Menu.Detail.laundryId(laundry.id))
                }
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = laundry.title
                )
                Text(
                    text = laundry.location
                )
            }
        }
    }
}