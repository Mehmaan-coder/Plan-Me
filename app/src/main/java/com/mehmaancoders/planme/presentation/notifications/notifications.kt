package com.mehmaancoders.planme.presentation.notifications

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.mehmaancoders.planme.R
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.presentation.navigation.Routes

@Composable
fun NotificationsScreen(navHostController: NavHostController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF7F3))
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.planme_back), contentDescription = null, tint = Color(0xFF3B2B20), )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Notifications", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color(0xFF3B2B20))
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFCFC0), RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("+11", color = Color(0xFFFF6B00), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            Image(
                painter = painterResource(id = R.drawable.profileicon), // replace with actual profile image
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape)
                    .clickable{navHostController.navigate(Routes.SettingsScreen)}
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Earlier This Day")

        NotificationCard(
            iconColor = Color(0xFFA7C879),
            icon = R.drawable.ic_robot,
            title = "Message from Planner AI!",
            subtitle = "52 Total Unread Messages"
        )

        NotificationCard(
            iconColor = Color(0xFFB9ABF8),
            icon = R.drawable.ic_clipboard,
            title = "Task Incomplete!",
            subtitle = "It’s Reflection Time!",
            endContent = {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF3F0FB)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("8/32", fontSize = 10.sp, color = Color(0xFF6E60D3))
                }
            }
        )

        NotificationCard(
            iconColor = Color(0xFF8C4D38),
            icon = R.drawable.ic_brain,
            title = "Exercise Complete!",
            subtitle = "22m Breathing Done."
        )

        NotificationCard(
            iconColor = Color(0xFFFFD338),
            icon = R.drawable.ic_stats,
            title = "Mental Health Data is Here.",
            subtitle = "Your Monthly Mental Analysis is here.",
            customContent = {
                OutlinedButton(
                    onClick = { /* Download PDF */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color(0xFF3B2B20))
                ) {
                    Icon(Icons.Default.FileDownload, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Lakao Data.pdf")
                }
            }
        )

        NotificationCard(
            iconColor = Color(0xFFA7C879),
            icon = R.drawable.ic_smile,
            title = "Mood Improved.",
            subtitle = "Neutral → Happy"
        )

        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle("Last Week")

        NotificationCard(
            iconColor = Color(0xFFFF6B00),
            icon = R.drawable.ic_health,
            title = "Stress Decreased.",
            subtitle = "Stress Level is now 3.",
            customContent = {
                // Stress progress bar
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    repeat(6) { index ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (index < 3) Color(0xFFFF6B00)
                                    else Color(0xFFE5E1DE)
                                )
                        )
                        if (index != 5) Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        )

        NotificationCard(
            iconColor = Color(0xFF6E4E33),
            icon = R.drawable.ic_ai,
            title = "Plan Me Recommendations.",
            subtitle = "48 Productivity Recommendations"
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color(0xFF3B2B20),
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun NotificationCard(
    iconColor: Color,
    icon: Int,
    title: String,
    subtitle: String,
    endContent: @Composable (() -> Unit)? = null,
    customContent: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
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
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF3B2B20))
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
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
