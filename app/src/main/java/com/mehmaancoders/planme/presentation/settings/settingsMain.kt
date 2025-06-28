package com.mehmaancoders.planme.presentation.settings

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

// --- Data Classes ---

data class SettingItem(
    val id: String,
    val title: String,
    val iconRes: Int,
    val trailingText: String? = null,
    val hasToggle: Boolean = false,
    val highlight: Boolean = false,
    var initialToggleState: Boolean? = null
)

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    val currentColorScheme = MaterialTheme.colorScheme
    var emergencyContactCount by remember { mutableStateOf(15) }

    // ✅ Get current Firebase user
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    val userName = user?.displayName ?: "Unknown User"
    val userEmail = user?.email ?: "Not Available"
    val userPhotoUrl = user?.photoUrl?.toString()

    val generalSettingsItems = remember(emergencyContactCount) {
        listOf(
            SettingItem("notifications", "Notifications", R.drawable.ic_notification),
            SettingItem("personal_info", "Personal Information", R.drawable.ic_user),
            SettingItem(
                "emergency_contact", "Emergency Contact", R.drawable.ic_warning,
                trailingText = "$emergencyContactCount+"
            ),
            SettingItem(
                "language", "Language", R.drawable.ic_language,
                trailingText = "English (EN)"
            ),
            SettingItem("invite_friends", "Invite Friends", R.drawable.ic_share),
            SettingItem("feedback", "Submit Feedback", R.drawable.ic_feedback)
        )
    }

    val securityPrivacyItems = listOf(
        SettingItem("security", "Security", R.drawable.ic_lock),
        SettingItem("help_center", "Help Center", R.drawable.ic_help)
    )

    val dangerZoneItems = listOf(
        SettingItem("close_account", "Close Account", R.drawable.ic_delete, highlight = true)
    )

    val logOutItems = listOf(
        SettingItem("log_out", "Log Out", R.drawable.ic_logout)
    )

    val onSettingItemClick = { itemId: String ->
        Log.i("SettingsScreen", "Clicked on setting item: $itemId")
        if (itemId == "emergency_contact") {
            emergencyContactCount++
        }
        // TODO: Add actual navigation logic for other screens
    }

    val onToggleChanged = { itemId: String, newState: Boolean ->
        Log.i("SettingsScreen", "Toggle changed for $itemId: $newState")
        generalSettingsItems.find { it.id == itemId }?.initialToggleState = newState
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(currentColorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(0xFF3B2B20), RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Settings", color = Color.White, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(12.dp))

                if (userPhotoUrl != null) {
                    AsyncImage(
                        model = userPhotoUrl,
                        contentDescription = "User Profile Picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.profileicon),
                        contentDescription = "User Profile Picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(userName, style = MaterialTheme.typography.titleMedium, color = Color.White)
                Text(userEmail, style = MaterialTheme.typography.bodyMedium, color = Color.LightGray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SettingsSection(
            title = "General Settings",
            items = generalSettingsItems,
            onSettingItemClick = onSettingItemClick,
            onToggleChanged = onToggleChanged
        )

        SettingsSection(
            title = "Security & Privacy",
            items = securityPrivacyItems,
            onSettingItemClick = onSettingItemClick
        )

        SettingsSection(
            title = "Danger Zone",
            items = dangerZoneItems,
            onSettingItemClick = onSettingItemClick
        )

        SettingsSection(
            title = "Log Out",
            items = logOutItems,
            onSettingItemClick = { itemId ->
                if (itemId == "log_out") {
                    FirebaseAuth.getInstance().signOut()
                    navHostController.navigate(Routes.SignInScreen) {
                        popUpTo(0) { inclusive = true } // Clear backstack
                    }
                } else {
                    onSettingItemClick(itemId)
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SettingsSection(
    title: String,
    items: List<SettingItem>,
    onSettingItemClick: (String) -> Unit,
    onToggleChanged: (itemName: String, newState: Boolean) -> Unit = { _, _ -> }
) {
    val currentColorScheme = MaterialTheme.colorScheme
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.titleSmall,
            color = currentColorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))

        items.forEach { item ->
            Card(
                onClick = { onSettingItemClick(item.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (item.highlight) Color(0xFFFFE5DC) else currentColorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                SettingRowContent(
                    item = item,
                    onToggleChanged = { newState -> onToggleChanged(item.id, newState) }
                )
            }
        }
    }
}

@Composable
fun SettingRowContent(
    item: SettingItem,
    onToggleChanged: (Boolean) -> Unit
) {
    val currentColorScheme = MaterialTheme.colorScheme
    val iconTintColor = if (item.highlight) Color(0xFFFF6B00) else currentColorScheme.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(currentColorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = "${item.title} Icon",
                tint = iconTintColor,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            item.title,
            style = MaterialTheme.typography.bodyLarge,
            color = currentColorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        when {
            item.hasToggle -> {
                Switch(
                    checked = item.initialToggleState ?: false,
                    onCheckedChange = onToggleChanged,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = currentColorScheme.primary,
                        checkedTrackColor = currentColorScheme.primaryContainer,
                        uncheckedThumbColor = currentColorScheme.outline,
                        uncheckedTrackColor = currentColorScheme.surfaceVariant,
                        uncheckedBorderColor = currentColorScheme.outline.copy(alpha = 0.5f)
                    )
                )
            }
            item.trailingText != null -> {
                Text(
                    item.trailingText,
                    color = currentColorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Navigate",
                    tint = currentColorScheme.onSurfaceVariant
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Navigate",
                    tint = currentColorScheme.onSurfaceVariant
                )
            }
        }
    }
}
