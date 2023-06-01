package com.example.dropdone.ui.components.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dropdone.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

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

@Composable
fun SearchBarLaundry(
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    val context = LocalContext.current

    TextField(
        value = searchText,
        onValueChange = { newSearch ->
            searchText = newSearch
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Bar"
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
        ),
        placeholder = {
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(stringResource(R.string.search))
                    }
                },
                onClick = {
                    Toast.makeText(context, "Search Bar Clicked", Toast.LENGTH_SHORT).show()
                }
            )
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
    )
}