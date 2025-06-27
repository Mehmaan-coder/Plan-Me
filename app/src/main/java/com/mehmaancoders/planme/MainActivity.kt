package com.mehmaancoders.planme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.FirebaseApp
import com.mehmaancoders.planme.presentation.navigation.PlanMeNavigationSystem
import com.mehmaancoders.planme.ui.theme.PlanMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase (Auth, Firestore, etc.)
        FirebaseApp.initializeApp(this)

        // Enable edge-to-edge display (for immersive UI)
        enableEdgeToEdge()

        // Set the Composable navigation system with app theme
        setContent {
            PlanMeTheme {
                PlanMeNavigationSystem()
            }
        }
    }
}
