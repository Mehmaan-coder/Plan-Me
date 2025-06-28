// Updated PlanMeNavigationSystem.kt
package com.mehmaancoders.planme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mehmaancoders.planme.presentation.authentication.AuthState
import com.mehmaancoders.planme.presentation.authentication.ConnectDevicesScreen
import com.mehmaancoders.planme.presentation.authentication.SignInScreen
import com.mehmaancoders.planme.presentation.authentication.SignUpScreen
import com.mehmaancoders.planme.presentation.authentication.rememberAuthState
import com.mehmaancoders.planme.presentation.chat.ChatBotInfoScreen
import com.mehmaancoders.planme.presentation.fetching.FetchingScreen
import com.mehmaancoders.planme.presentation.goalselection.BudgetSelectorScreen
import com.mehmaancoders.planme.presentation.goalselection.ExperienceLevelRatingScreen
import com.mehmaancoders.planme.presentation.goalselection.GoalSelectionScreen
import com.mehmaancoders.planme.presentation.goalselection.MoodSelectorScreen
import com.mehmaancoders.planme.presentation.goalselection.TimeSelectorScreen
import com.mehmaancoders.planme.presentation.goalselection.TimeSelectionMode
import com.mehmaancoders.planme.presentation.home.HomeScreen
import com.mehmaancoders.planme.presentation.insights.InsightsScreen
import com.mehmaancoders.planme.presentation.notifications.NotificationsScreen
import com.mehmaancoders.planme.presentation.settings.SettingsScreen
import com.mehmaancoders.planme.presentation.splashscreen.SplashScreen
import com.mehmaancoders.planme.presentation.welcomescreen.*

@Composable
fun PlanMeNavigationSystem() {
    val navController = rememberNavController()
    val authState = rememberAuthState()

    // Handle navigation based on auth state
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                // User is authenticated, navigate to main app if not already there
                val currentRoute = navController.currentDestination?.route
                if (currentRoute == Routes.SplashScreen::class.qualifiedName ||
                    currentRoute == Routes.SignInScreen::class.qualifiedName ||
                    currentRoute == Routes.SignUpScreen::class.qualifiedName ||
                    currentRoute?.contains("WelcomeScreen") == true) {

                    navController.navigate(Routes.HomeScreen) {
                        popUpTo(0) { inclusive = true } // Clear entire back stack
                    }
                }
            }
            is AuthState.Unauthenticated -> {
                // User is not authenticated, ensure they're on auth/welcome screens
                val currentRoute = navController.currentDestination?.route
                if (currentRoute == Routes.ConnectDevicesScreen::class.qualifiedName ||
                    currentRoute == Routes.HomeScreen::class.qualifiedName ||
                    currentRoute == Routes.GoalSelectionScreen::class.qualifiedName ||
                    currentRoute == Routes.TimeSelectorScreen::class.qualifiedName ||
                    currentRoute == Routes.BudgetSelectorScreen::class.qualifiedName ||
                    currentRoute == Routes.FetchingScreen::class.qualifiedName) {

                    navController.navigate(Routes.SignInScreen) {
                        popUpTo(0) { inclusive = true } // Clear entire back stack
                    }
                }
            }
            is AuthState.Loading -> {
                // Still loading, let splash screen handle it
            }
        }
    }

    NavHost(startDestination = Routes.SplashScreen, navController = navController) {

        composable<Routes.SplashScreen> {
            SplashScreen(navController, authState)
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

        composable<Routes.HomeScreen> {
            HomeScreen(navController)
        }

        composable<Routes.GoalSelectionScreen> {
            GoalSelectionScreen(navController)
        }

        composable<Routes.TimeSelectorScreen> {
            TimeSelectorScreen(
                navController = navController,
                initialMode = TimeSelectionMode.HOURS_PER_DAY
            )
        }

        composable<Routes.BudgetSelectorScreen> {
            BudgetSelectorScreen(navController)
        }

        composable<Routes.FetchingScreen> {
            FetchingScreen(navController)
        }

        composable<Routes.MoodSelectorScreen> {
            MoodSelectorScreen(navController)
        }

        composable<Routes.ExperienceLevelRatingScreen>{
            ExperienceLevelRatingScreen(navController)
        }

        composable<Routes.NotificationScreen>{
            NotificationsScreen(navController)
        }

        composable<Routes.SettingsScreen>{
            SettingsScreen(navController)
        }

        composable<Routes.ChatBotInfoScreen>{
            ChatBotInfoScreen(navController)
        }

        composable<Routes.InsightsScreen>{
            InsightsScreen(navController)
        }
    }
}