package com.mehmaancoders.planme.presentation.settings

import android.util.Log // For logging actions
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
// Explicitly import Material 3 components
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme // M3 MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
// Icons are fine
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mehmaancoders.planme.R // Ensure this R file is correct for your project

// --- Data Classes ---

data class UserProfile(
    val name: String,
    val email: String,
    val location: String,
    val avatarRes: Int // e.g., R.drawable.placeholder_avatar
)

data class SettingItem(
    val id: String, // Unique ID for handling clicks/toggles
    val title: String,
    val iconRes: Int,
    val trailingText: String? = null,
    val hasToggle: Boolean = false, // Kept for potential other toggles
    val highlight: Boolean = false,
    var initialToggleState: Boolean? = null // For other potential toggles
)

// --- Dummy Data ---

val sampleUserProfile = UserProfile(
    name = "Lakao Developer",
    email = "lakao.dev@example.com",
    location = "Jaipur, India",
    avatarRes = R.drawable.profileicon // Replace with your actual placeholder drawable
)

// --- Composable Screen ---

@Composable
fun SettingsScreen() {
    // This Composable will now use the MaterialTheme provided by its parent.
    // If no specific theme is provided, it defaults to system settings for previews.
    val currentColorScheme = MaterialTheme.colorScheme

    var emergencyContactCount by remember { mutableStateOf(15) }

    val generalSettingsItems = remember(emergencyContactCount) { // Removed isDarkModeEnabled dependency
        listOf(
            SettingItem("notifications", "Notifications", R.drawable.ic_notification),
            SettingItem("personal_info", "Personal Information", R.drawable.ic_user),
            SettingItem(
                "emergency_contact",
                "Emergency Contact",
                R.drawable.ic_warning,
                trailingText = "$emergencyContactCount+"
            ),
            SettingItem(
                "language",
                "Language",
                R.drawable.ic_language,
                trailingText = "English (EN)"
            ),
            // "Dark Mode" item removed
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
        // Handle other item clicks (e.g., navigation)
    }

    val onToggleChanged = { itemId: String, newState: Boolean ->
        Log.i("SettingsScreen", "Toggle changed for $itemId: $newState")
        // Handle any other toggles if they exist.
        // For example, if you had a "Enable Feature X" toggle:
        // if (itemId == "feature_x") { /* update state for feature x */ }
        generalSettingsItems.find { it.id == itemId }?.initialToggleState = newState
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(currentColorScheme.background) // Use background from the ambient theme
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    Color(0xFF3B2B20), // This custom color remains
                    RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Settings",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(12.dp))

                Image(
                    painter = painterResource(id = sampleUserProfile.avatarRes),
                    contentDescription = "User Profile Picture of ${sampleUserProfile.name}",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    sampleUserProfile.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    sampleUserProfile.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
                Text(
                    sampleUserProfile.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SettingsSection(
            title = "General Settings",
            items = generalSettingsItems,
            onSettingItemClick = onSettingItemClick,
            onToggleChanged = onToggleChanged // Pass if other toggles exist
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
                    Log.w("SettingsScreen", "User initiated Log Out!")
                    // TODO: Implement actual log out logic
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
            Card( // Or Surface
                onClick = { onSettingItemClick(item.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp), // Add some spacing between cards
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (item.highlight) Color(0xFFFFE5DC) else currentColorScheme.surfaceVariant // Or a more distinct color
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Add some shadow
            ) {
                SettingRowContent( // Extract the content of your current SettingRow into a new composable
                    item = item,
                    onToggleChanged = { newState -> onToggleChanged(item.id, newState) }
                )
            }
            // Spacer(modifier = Modifier.height(8.dp)) // May not be needed if Card has padding
        }
        Spacer(modifier = Modifier.height(1.dp))
    }
}

@Composable
fun SettingRowContent(
    item: SettingItem,
    onToggleChanged: (Boolean) -> Unit
    // You might need to pass currentColorScheme here or access it via MaterialTheme.colorScheme
) {
    val currentColorScheme = MaterialTheme.colorScheme
    val primaryTextColor = currentColorScheme.onSurface
    val secondaryTextColor = currentColorScheme.onSurfaceVariant
    val iconBoxBackgroundColor = currentColorScheme.surfaceVariant // Or a different color if card uses surfaceVariant
    val iconTintColor = if (item.highlight) Color(0xFFFF6B00) else currentColorScheme.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            // .clip(RoundedCornerShape(20.dp)) // Shape is now on the Card
            // .background(rowBackgroundColor) // Background is now on the Card
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ... rest of your SettingRow content (Icon, Text, Switch/Arrow)
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconBoxBackgroundColor), // Consider adjusting if Card color is similar
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
            color = primaryTextColor,
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
                    color = secondaryTextColor,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "Navigate",
                    tint = secondaryTextColor
                )
            }
            else -> {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "Navigate",
                    tint = secondaryTextColor
                )
            }
        }
    }
}
        @Composable
fun SettingRow(
    item: SettingItem,
    onClick: () -> Unit,
    onToggleChanged: (Boolean) -> Unit
) {
    val currentColorScheme = MaterialTheme.colorScheme

    // Simplified color logic, relying on the ambient theme
    val rowBackgroundColor = if (item.highlight) Color(0xFFFFE5DC) else currentColorScheme.surface
    val primaryTextColor = currentColorScheme.onSurface
    val secondaryTextColor = currentColorScheme.onSurfaceVariant
    val iconBoxBackgroundColor = currentColorScheme.surfaceVariant
    val iconTintColor = if (item.highlight) Color(0xFFFF6B00) else currentColorScheme.primary


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(rowBackgroundColor)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconBoxBackgroundColor),
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
            color = primaryTextColor,
            modifier = Modifier.weight(1f)
        )

        when {
            item.hasToggle -> { // This part is kept for any other potential toggles
                Switch(
                    checked = item.initialToggleState ?: false,
                    onCheckedChange = onToggleChanged,
                    colors = SwitchDefaults.colors( // Uses M3 theme colors
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
                    color = secondaryTextColor,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "Navigate",
                    tint = secondaryTextColor
                )
            }
            else -> {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "Navigate",
                    tint = secondaryTextColor
                )
            }
        }
    }
}

// --- Preview ---

@Preview(name = "Settings Screen Preview", showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    // The preview will use the default light/dark theme based on system settings
    // or the theme defined at the app level if this preview is part of a larger app.
    MaterialTheme { // Ensures M3 theming context for the preview
        SettingsScreen()
    }
}
