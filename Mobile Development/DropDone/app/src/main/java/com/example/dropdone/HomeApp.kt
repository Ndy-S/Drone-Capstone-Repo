package com.example.dropdone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dropdone.pages.HomePage
import com.example.dropdone.ui.components.Profile
import com.example.dropdone.ui.components.SearchBar

@Composable
fun HomeApp(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        HomePage()
    }
}