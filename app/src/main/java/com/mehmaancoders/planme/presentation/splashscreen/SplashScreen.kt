// Updated SplashScreen.kt
package com.mehmaancoders.planme.presentation.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.mehmaancoders.planme.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.presentation.authentication.AuthState
import com.mehmaancoders.planme.presentation.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    authState: AuthState
) {
    LaunchedEffect(authState) {
        // Add a minimum delay for splash screen visibility
        delay(1500)

        when (authState) {
            is AuthState.Loading -> {
                // Still checking auth state, wait a bit more
                delay(500)
            }
            is AuthState.Authenticated -> {
                // User is already signed in, navigate to main app
                navHostController.navigate(Routes.ConnectDevicesScreen) {
                    popUpTo<Routes.SplashScreen> {
                        inclusive = true
                    }
                }
            }
            is AuthState.Unauthenticated -> {
                // User is not signed in, navigate to welcome screen
                navHostController.navigate(Routes.WelcomeScreen) {
                    popUpTo<Routes.SplashScreen> {
                        inclusive = true
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.planmelogo),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = "Plan-Me",
                fontSize = 50.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.brown)
            )
        }
    }
}