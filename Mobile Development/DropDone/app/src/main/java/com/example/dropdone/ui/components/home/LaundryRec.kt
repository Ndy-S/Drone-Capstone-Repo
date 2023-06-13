package com.example.dropdone.ui.components.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.dropdone.R
import com.example.dropdone.data.LaundryRepository
import com.example.dropdone.model.Laundry
import com.example.dropdone.MainViewModel
import com.example.dropdone.ViewModelFactory
import com.example.dropdone.ui.navigation.Menu

@Composable
fun LaundryRec(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel(factory = ViewModelFactory(LaundryRepository()))
) {
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
        LaundryList(modifier = Modifier, navController = navController)
    }
}

@Composable
fun LaundryList(
    modifier: Modifier,
    navController: NavController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel(factory = ViewModelFactory(LaundryRepository()))
) {
    val laundryColl = remember { mutableStateListOf<Laundry>() }

    LaunchedEffect(Unit) {
        val laundryData = mainViewModel.getLaundryFromRepo()
        for (document in laundryData) {
            laundryColl.add(document)
        }
    }
    LazyColumn {
        items(laundryColl.size) {
            for (document in laundryColl) {
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
                            .clickable{
                                navController.navigate(Menu.Detail.laundryId(document.id))
                            }
                    ) {
                        AsyncImage(
                            model = document.icon,
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
                                    text = document.laundry_name,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = ("Rate " + document.rating.toString()),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.offset(x = 67.dp)
                                )
                            }
                            Text(
                                text = document.address,
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
