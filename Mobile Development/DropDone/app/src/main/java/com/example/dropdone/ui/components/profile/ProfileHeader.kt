package com.example.dropdone.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import com.example.dropdone.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Profile User",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(R.string.username),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .paddingFromBaseline(32.dp, 16.dp)
        )
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier
) {
//    var userName by remember { mutableStateOf("") }
//    var userEmail by remember { mutableStateOf("") }
//    var userPassword by remember { mutableStateOf("") }
//    var userPasswordVisible by remember { mutableStateOf(false) }
    var userAddress by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
//        Text(stringResource(R.string.username))
//        OutlinedTextField(
//            value = userName,
//            onValueChange = { newUserName ->
//                userName = newUserName
//            },
//            label = { Text("Username")},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 20.dp)
//        )
//
//        Text(stringResource(R.string.email))
//        OutlinedTextField(
//            value = userEmail,
//            onValueChange = { newUserEmail ->
//                userEmail = newUserEmail
//            },
//            label = { Text("Email")},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 20.dp)
//        )
//
//        Text(stringResource(R.string.password))
//        OutlinedTextField(
//            value = userPassword,
//            onValueChange = { newUserPassword ->
//                userPassword = newUserPassword
//            },
//            label = { Text("Password")},
//            visualTransformation = if (userPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            trailingIcon = {
//                IconButton(onClick = { userPasswordVisible = !userPasswordVisible }) {
//                    Icon(
//                        painter = painterResource(
//                            id = if (userPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
//                        ),
//                        contentDescription = if (userPasswordVisible) "Hide Password" else "Show Password"
//                    )
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 20.dp)
//        )

        Text(stringResource(R.string.current_loc))
        OutlinedTextField(
            value = userAddress,
            onValueChange = { newUserAddress ->
                userAddress = newUserAddress
            },
            label = { Text("Lokasi kamu saat ini")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
        )

        Button(
            onClick = {

            },
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.dark_blue)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                stringResource(R.string.profile_update),
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}