package com.example.dropdone.ui.components.side

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import coil.compose.rememberAsyncImagePainter
import com.example.dropdone.model.UserData


@Composable
fun ProfileHeader(
    userData: UserData?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userData != null) {
            Image(
                painter = rememberAsyncImagePainter(userData.profilePictureUrl),
                contentDescription = "Profile User",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            userData.username?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .paddingFromBaseline(32.dp, 16.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileContent(
    userData: UserData?,
    modifier: Modifier = Modifier
) {
    var userName by remember { mutableStateOf(userData?.username) }
    var userEmail by remember { mutableStateOf(userData?.email) }
    var userPassword by remember { mutableStateOf(userData?.password) }
    var userPasswordVisible by remember { mutableStateOf(false) }
    var userAddress by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(stringResource(R.string.username))
        OutlinedTextField(
            value = userName.toString(),
            enabled = false,
            onValueChange = { newUserName ->
                userName = newUserName
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        Text(stringResource(R.string.email))
        OutlinedTextField(
            value = userEmail.toString(),
            enabled = false,
            onValueChange = { newUserEmail ->
                userEmail = newUserEmail
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        Text(stringResource(R.string.password))
        OutlinedTextField(
            value = userPassword.toString(),
            enabled = false,
            onValueChange = { newUserPassword ->
                userPassword = newUserPassword
            },
            visualTransformation = if (userPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { userPasswordVisible = !userPasswordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (userPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                        ),
                        contentDescription = if (userPasswordVisible) "Hide Password" else "Show Password"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

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
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                stringResource(R.string.profile_update),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )
        }
    }
}