package com.example.dropdone

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dropdone.auth.AuthViewModel
import com.example.dropdone.auth.GAuth
import com.example.dropdone.auth.GAuthUi
import com.example.dropdone.model.UserData
import com.example.dropdone.ui.theme.DropDoneTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class MainActivity : ComponentActivity() {

    private val gAuth by lazy {
        GAuth(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropDoneTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        val viewModel = viewModel<AuthViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        SplashScreen(navController = navController, key1 = state.isSignInSuccessful)
                        viewModel.resetState()
                    }
                    composable("sign_in") {
                        val viewModel = viewModel<AuthViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val signInResult = gAuth.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                        viewModel.onSignInResult(signInResult)
                                    }
                                }
                            }
                        )

                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if (state.isSignInSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    "Sign in successful",
                                    Toast.LENGTH_LONG
                                ).show()

                                val userData = gAuth.getSignedInUser()
                                if (userData != null) {
                                    checkUserRegistration(userData)
                                }

                                navController.navigate("validation")
                                viewModel.resetState()
                            }
                        }

                        GAuthUi(
                            state = state,
                            onSignInClick = {
                                lifecycleScope.launch {
                                    val signInIntentSender = gAuth.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            }
                        )
                    }
                    composable("validation") {
                        Validation(
                            client,
                            userData = gAuth.getSignedInUser(),
                            onSignOut = {
                                lifecycleScope.launch {
                                    gAuth.signOut()
                                    Toast.makeText(
                                        applicationContext,
                                        "Signed out",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.popBackStack()
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun checkUserRegistration(userData: UserData) {
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        val query = usersCollection.whereEqualTo("email", userData.email)

        query.get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    val user = UserData(
                        userId = userData.userId,
                        username = userData.username ?: "N/A",
                        email = userData.email ?: "N/A",
                        address = "N/A",
                        profilePictureUrl = userData.profilePictureUrl
                    )
                    usersCollection.document(userData.userId.toString())
                        .set(
                            user,
                            SetOptions.mergeFields(
                                listOf(
                                    "username",
                                    "email",
                                    "address",
                                    "profilePictureUrl"
                                )
                            )
                        )
                        .addOnSuccessListener {
                            Toast.makeText(
                                applicationContext,
                                "User registered successfully",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                applicationContext,
                                "Failed to register user: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                } else {
                    // User is already registered
                    Toast.makeText(
                        applicationContext,
                        "User already registered",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    applicationContext,
                    "Failed to check user registration: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}