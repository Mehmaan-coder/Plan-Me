package com.mehmaancoders.planme.presentation.home_insights

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment

@Composable
@Preview(showSystemUi = true)
fun InsightsScreen() {
    val backgroundColor = Color(0xFFFDF7F3)
    val primaryTextColor = Color(0xFF3B2B20)
    val moodHappyColor = Color(0xFFA7C879)
    val moodNeutralColor = Color(0xFFB7794C)
    val moodSadColor = Color(0xFFFF7337)

    val selectedTab = remember { mutableStateOf("All") }
    val tabs = listOf("All", "Days", "Weeks", "Months", "Years")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = primaryTextColor)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(primaryTextColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.BarChart, contentDescription = null, tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Insights",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = primaryTextColor
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "See your progress throughout the day.",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Filter Tabs
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(32.dp))
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabs.forEach {
                val isSelected = it == selectedTab.value
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .background(if (isSelected) primaryTextColor else Color.Transparent)
                        .clickable { selectedTab.value = it }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = it,
                        color = if (isSelected) Color.White else primaryTextColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Simulated Mood Graph Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            // For now, we use circles and labels as placeholders
            val moodPoints = listOf(
                Triple("Happy", moodHappyColor, 0.2f),
                Triple("Depressed", moodSadColor, 0.6f),
                Triple("Neutral", moodNeutralColor, 0.4f),
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    "Mon",
                    "Tue",
                    "Wed",
                    "Thu",
                    "Fri",
                    "Sat",
                    "Sun"
                ).forEachIndexed { index, label ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        moodPoints.getOrNull(index % moodPoints.size)
                            ?.let { (moodLabel, moodColor, _) ->
                                Text(
                                    text = moodLabel,
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .background(
                                            moodColor,
                                            RoundedCornerShape(16.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(moodColor)
                                )
                            } ?: Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            // Dotted horizontal lines (simulated)
            repeat(3) {
                Divider(
                    color = Color(0xFFE8D8D0),
                    thickness = 1.dp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = (it + 1) * 50.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // AI Mood Predictions
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "AI Mood Predictions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = primaryTextColor
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, primaryTextColor, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Android,
                        contentDescription = null,
                        tint = primaryTextColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Next 1w", color = primaryTextColor, fontSize = 14.sp)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = primaryTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Mood emoji list
            val moods = listOf(
                "Mon" to "😊", "Tue" to "😄", "Wed" to "😔",
                "Thu" to "🙂", "Fri" to "😐", "Sat" to "😕", "Sun" to "😞"
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                moods.forEach { (day, emoji) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF5F5F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = emoji, fontSize = 22.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(day, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(64.dp)) // Space for bottom bar
    }

}

