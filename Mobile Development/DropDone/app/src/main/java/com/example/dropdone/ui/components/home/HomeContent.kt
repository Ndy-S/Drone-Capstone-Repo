package com.example.dropdone.ui.components.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dropdone.R
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.data.LaundryRepository
import com.example.dropdone.model.DataLaundryDummy
import com.example.dropdone.ui.MainViewModel
import com.example.dropdone.ui.ViewModelFactory
import com.example.dropdone.ui.navigation.Menu

@Composable
fun Profile(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.profile),
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
            Text(
                text = stringResource(R.string.username),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarLaundry(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel(factory = ViewModelFactory(LaundryRepository()))
) {
    val laundryList by mainViewModel.laundryList.collectAsState()
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
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(laundryList) { laundry ->
                        LaundrySearchList(
                            laundry = laundry,
                            modifier = Modifier.fillMaxWidth(),
                            navController = navController
                        )
                    }
                }
            }
        }
        GMapView()
        LaundryRec(navController = navController)
    }
}

@Composable
fun LaundrySearchList(
    laundry: DataLaundryDummy,
    modifier: Modifier,
    navController: NavController = rememberNavController()
) {
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
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
                    text = laundry.title,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = laundry.location,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

