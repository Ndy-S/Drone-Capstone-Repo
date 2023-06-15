package com.example.dropdone.ui.components.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dropdone.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.dropdone.data.LaundryRepository
import com.example.dropdone.MainViewModel
import com.example.dropdone.ViewModelFactory
import com.example.dropdone.model.Laundry
import com.example.dropdone.model.UserData
import com.example.dropdone.ui.navigation.Menu
import okhttp3.OkHttpClient

@Composable
fun Profile(
    userData: UserData?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (userData != null) {
            Image(
                painter = rememberAsyncImagePainter(userData.profilePictureUrl),
                contentDescription = "Profile User",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.hello),
                )
                userData.username?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarLaundry(
    client: OkHttpClient,
    userData: UserData?,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel(factory = ViewModelFactory(LaundryRepository()))
) {
    val query by mainViewModel.query
    var isSearching by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            Modifier.fillMaxWidth()
        ) {
            SearchBar(
                modifier = Modifier.align(Alignment.Center),
                query = query,
                onQueryChange = mainViewModel::search,
                onSearch = { isSearching = false },
                active = isSearching,
                onActiveChange = {
                    isSearching = it
                },
                placeholder = { Text(stringResource(R.string.search)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            ) {
                LaundrySearchList(client, modifier = Modifier, navController = navController)
            }
        }
        GMapView()
        LaundryRec(client, userData, navController = navController)
    }
}

@Composable
fun LaundrySearchList(
    client: OkHttpClient,
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
                    border = BorderStroke(2.dp, Color.LightGray),
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
                            .clickable {
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


