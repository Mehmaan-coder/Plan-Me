package com.mehmaancoders.planme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mehmaancoders.planme.presentation.authentication.ConnectDevicesScreen
import com.mehmaancoders.planme.presentation.authentication.SignInScreen
import com.mehmaancoders.planme.presentation.authentication.SignUpScreen
import com.mehmaancoders.planme.presentation.home.HomeScreen
import com.mehmaancoders.planme.presentation.splashscreen.SplashScreen
import com.mehmaancoders.planme.presentation.welcomescreen.*

@Composable
fun PlanMeNavigationSystem() {

    val navController = rememberNavController()

    NavHost(startDestination = Routes.SplashScreen, navController = navController) {

        composable<Routes.SplashScreen> {
            SplashScreen(navController)
        }

        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController)
        }

        composable<Routes.WelcomeScreen2> {
            WelcomeScreen2(navController)
        }

        composable<Routes.WelcomeScreen3> {
            WelcomeScreen3(navController)
        }

        composable<Routes.WelcomeScreen4> {
            WelcomeScreen4(navController)
        }

        composable<Routes.WelcomeScreen5> {
            WelcomeScreen5(navController)
        }

        composable<Routes.WelcomeScreen6> {
            WelcomeScreen6(navController)
        }

        composable<Routes.SignInScreen> {
            SignInScreen(navController)
        }

        composable<Routes.SignUpScreen> {
            SignUpScreen(navController)
        }

        composable<Routes.ConnectDevicesScreen> {
            ConnectDevicesScreen(navController)
        }

        // ✅ HomeScreen with arguments
        composable<Routes.HomeScreen> {
            HomeScreen(navController)
        }
    }
}
