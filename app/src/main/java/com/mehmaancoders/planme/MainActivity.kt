package com.mehmaancoders.planme

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.firebase.FirebaseApp
import com.mehmaancoders.planme.presentation.navigation.PlanMeNavigationSystem
import com.mehmaancoders.planme.ui.theme.PlanMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val GOOGLE_CALENDAR_SIGN_IN_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Initialize Firebase
        FirebaseApp.initializeApp(this)

        // ✅ Enable immersive UI
        enableEdgeToEdge()

        // ✅ Set the main Composable
        setContent {
            PlanMeTheme {
                PlanMeNavigationSystem()
            }
        }
    }

    // ✅ Handle Google Sign-In result for Calendar access
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_CALENDAR_SIGN_IN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                // 🎯 Now use this account to access Google Calendar API
                val credential = GoogleAccountCredential.usingOAuth2(
                    this, listOf(CalendarScopes.CALENDAR)
                )
                credential.selectedAccount = account.account

                val service = Calendar.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    GsonFactory.getDefaultInstance(),
                    credential
                ).setApplicationName("PlanMe").build()

                Thread {
                    try {
                        val calendarList = service.calendarList().list().execute()
                        val calendars = calendarList.items.map { it.summary }
                        runOnUiThread {
                            Toast.makeText(this, "Connected to: ${calendars.joinToString()}", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this, "Calendar access error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }.start()

            } catch (e: ApiException) {
                Toast.makeText(this, "Sign-in failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}