package com.mehmaancoders.planme.presentation.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.calendar.CalendarScopes
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

@Composable
fun ConnectDevicesScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val devices = listOf("Google Calendar")
    var selectedDevices by remember { mutableStateOf(setOf<String>()) }
    var showPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF7F4))
            .padding(vertical = 16.dp, horizontal = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .padding(5.dp)
                .border(width = 2.dp, color = colorResource(id = R.color.brown), shape = CircleShape)
        ) {
            IconButton(onClick = { navHostController.navigate(Routes.SignInScreen) }) {
                Image(
                    painter = painterResource(id = R.drawable.planme_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "Connect to your Space",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4C2B1C)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Link your favourite Productivity Services & Smart-home devices/apps.",
                fontSize = 18.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
        ) {
            items(devices) { device ->
                DeviceCard(
                    name = device,
                    isSelected = selectedDevices.contains(device),
                    onClick = {
                        selectedDevices = if (selectedDevices.contains(device)) {
                            selectedDevices - device
                        } else {
                            selectedDevices + device
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (selectedDevices.contains("Google Calendar")) {
                            linkGoogleCalendar(context) {
                                showPopup = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C2B1C))
                ) {
                    Text("Link Now", color = Color.White, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (showPopup) {
            ConnectedPopup(
                onDismiss = { showPopup = false },
                onClick = { navHostController.navigate(Routes.HomeScreen) }
            )
        }
    }
}

@Composable
fun DeviceCard(name: String, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) Color(0xFFB4D48B) else Color.Transparent
    val backgroundColor = Color.White

    val iconMap = mapOf(
        "Google Calendar" to R.drawable.google_calendar
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(30.dp))
            .border(2.dp, borderColor, shape = RoundedCornerShape(30.dp))
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            color = Color.Transparent
        ) {
            Image(
                painter = painterResource(id = iconMap[name] ?: R.drawable.google),
                contentDescription = "$name Icon",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp),
                alignment = Alignment.Center
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = name,
            color = Color(0xFF4C2B1C),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ConnectedPopup(onDismiss: () -> Unit, onClick: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(24.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.connected_illustration),
                    contentDescription = "Connected",
                    modifier = Modifier.size(180.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Your services have been connected",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4C2B1C),
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Set other preferences in the third party apps 🔑",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        onDismiss()
                        onClick()
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C2B1C))
                ) {
                    Text("Continue", color = Color.White)
                }
            }
        }
    }
}

/**
 * Launches Google Sign-In with Calendar Scope
 */
fun linkGoogleCalendar(context: Context, onSuccess: () -> Unit) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestScopes(Scope(CalendarScopes.CALENDAR))
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)
    val signInIntent: Intent = googleSignInClient.signInIntent

    if (context is Activity) {
        context.startActivityForResult(signInIntent, 1001) // handle in MainActivity
        onSuccess()
    }
}
