package com.mehmaancoders.planme.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object SplashScreen : Routes()

    @Serializable
    data object WelcomeScreen : Routes()

    @Serializable
    data object WelcomeScreen2 : Routes()

    @Serializable
    data object WelcomeScreen3 : Routes()

    @Serializable
    data object WelcomeScreen4 : Routes()

    @Serializable
    data object WelcomeScreen5 : Routes()

    @Serializable
    data object WelcomeScreen6 : Routes()

    @Serializable
    data object SignInScreen : Routes()

    @Serializable
    data object SignUpScreen : Routes()

    @Serializable
    data object ConnectDevicesScreen : Routes()

    // ✅ HomeScreen now accepts arguments
    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object GoalSelectionScreen : Routes()

    @Serializable
    data object BudgetSelectorScreen : Routes()

    @Serializable
    data object MoodSelectorScreen : Routes()

    @Serializable
    data object FetchingScreen : Routes()

    @Serializable
    data object TimeSelectorScreen : Routes()

    @Serializable
    data object ExperienceLevelRatingScreen : Routes()

    @Serializable
    data object NotificationScreen : Routes()

    @Serializable
    data object SettingsScreen : Routes()

}