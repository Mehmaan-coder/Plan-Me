package com.mehmaancoders.planme.presentation.notifications

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.data.model.NotificationItem

// 🎨 Colors
val screenBackground = Color(0xFFFDF7F3)
val primaryTextColor = Color(0xFF3B2B20)
val notificationCountBg = Color(0xFFFFCFC0)
val notificationCountText = Color(0xFFFF6B00)
val subtitleText = Color.Gray
val cardBackground = Color.White

val robotColor = Color(0xFFA7C879)
val clipboardColor = Color(0xFFB9ABF8)
val smileColor = Color(0xFFA7C879)
val healthColor = Color(0xFFFF6B00)
val statsColor = Color(0xFFFFD338)
val brainColor = Color(0xFF8C4D38)
val defaultColor = Color(0xFF6E4E33)

val taskProgressBg = Color(0xFFF3F0FB)
val taskProgressText = Color(0xFF6E60D3)

@Composable
fun NotificationsScreen(viewModel: NotificationViewModel = viewModel()) {
    val notifications by viewModel.notifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // 🔔 Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = primaryTextColor
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Notifications",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = primaryTextColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (notifications.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .background(notificationCountBg, RoundedCornerShape(16.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "+${notifications.size}",
                            color = notificationCountText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Default profile icon used
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User Profile",
                tint = primaryTextColor,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape)
                    .padding(6.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (notifications.isEmpty()) {
            SectionTitle("No Notifications Yet")
        } else {
            SectionTitle("Recent Notifications")

            notifications.forEach { notif ->
                val lowerTitle = notif.title.lowercase()

                val iconRes = when {
                    "planner" in lowerTitle || "ai" in lowerTitle -> R.drawable.ic_robot
                    "task" in lowerTitle || "incomplete" in lowerTitle -> R.drawable.ic_clipboard
                    "mood" in lowerTitle || "happy" in lowerTitle -> R.drawable.ic_smile
                    "stress" in lowerTitle -> R.drawable.ic_health
                    "mental" in lowerTitle || "analysis" in lowerTitle -> R.drawable.ic_stats
                    "exercise" in lowerTitle || "breathing" in lowerTitle -> R.drawable.ic_brain
                    else -> R.drawable.ic_robot // Default
                }

                val iconColor = when (iconRes) {
                    R.drawable.ic_robot -> robotColor
                    R.drawable.ic_clipboard -> clipboardColor
                    R.drawable.ic_smile -> smileColor
                    R.drawable.ic_health -> healthColor
                    R.drawable.ic_stats -> statsColor
                    R.drawable.ic_brain -> brainColor
                    else -> defaultColor
                }

                val endContent: @Composable (() -> Unit)? =
                    if ("task" in lowerTitle || "incomplete" in lowerTitle) {
                        {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(taskProgressBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "8/32", // Placeholder
                                    fontSize = 10.sp,
                                    color = taskProgressText
                                )
                            }
                        }
                    } else null

                NotificationCard(
                    iconColor = iconColor,
                    iconResId = iconRes,
                    title = notif.title,
                    subtitle = notif.message,
                    endContent = endContent
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = primaryTextColor,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun NotificationCard(
    iconColor: Color,
    @DrawableRes iconResId: Int,
    title: String,
    subtitle: String,
    endContent: @Composable (() -> Unit)? = null,
    customContent: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(cardBackground)
            .padding(16.dp)
            .padding(bottom = if (customContent != null) 8.dp else 0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconColor),
                contentAlignment = Alignment.Center
            ) {
                // Using try-catch for painterResource might not be the most idiomatic Compose way.
                // Consider using a placeholder or error state if the resource is invalid.
                // For simplicity, this try-catch remains for now.
                val painter = painterResource(id = iconResId)
                if (painter != null) {
                    Icon(
                        painter = painter,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = primaryTextColor)
                Text(subtitle, fontSize = 12.sp, color = subtitleText)
            }

            endContent?.let {
                Spacer(modifier = Modifier.width(8.dp))
                it()
            }
        }

        customContent?.let {
            Spacer(modifier = Modifier.height(12.dp))
            it()
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
}
