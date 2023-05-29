package com.example.dropdone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropdone.R

@Composable
fun SettingContent(
    modifier: Modifier = Modifier
) {
    var recLoc by remember { mutableStateOf(false) }
    var recRate by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.recommendation),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyToggle(checked = recLoc, onCheckChanged = { recLoc = it })
            Text(
                text = stringResource(R.string.recommendation_loc),
                Modifier.clickable {
                    recLoc = !recLoc
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyToggle(checked = recRate, onCheckChanged = { recRate = it })
            Text(
                text = stringResource(R.string.recommendation_rate),
                Modifier.clickable {
                    recRate = !recRate
                }
            )
        }
    }
}

@Composable
fun MyToggle(
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = {
            onCheckChanged(it)
        }
    )
}