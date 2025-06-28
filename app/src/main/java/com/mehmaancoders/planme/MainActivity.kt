package com.mehmaancoders.planme

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import com.mehmaancoders.planme.presentation.navigation.PlanMeNavigationSystem
import com.mehmaancoders.planme.ui.theme.PlanMeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔔 Ask for Notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1 // requestCode (can be any int)
            )
        }

        enableEdgeToEdge()
        setContent {
            PlanMeTheme {
                PlanMeNavigationSystem()
            }
        }
    }
}
