package com.mehmaancoders.planme.ui // Assuming this is your package name

import androidx.compose.foundation.* // Keep this
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.* // For M3 Card, FloatingActionButton, Icon, Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
// import com.mehmaancoders.planme.R // Usually not needed directly in composable files

@Preview(showSystemUi = true)
@Composable
fun DayWiseTasksScreenPreview() {
    DayWiseTasksScreen()
}

@Composable
fun DayWiseTasksScreen() {
    Box(modifier = Modifier.fillMaxSize()) { // Main container
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F6F3))
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF3B2B20))
                    .padding(horizontal = 16.dp, vertical = 32.dp)
            ) {
                Column {
                    Text(
                        text = "Your Plan",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Days Row
                    val days = listOf("12" to "Mon", "13" to "Tue", "14" to "Wed", "15" to "Thu", "16" to "Fri", "17" to "Sat", "18" to "Sun")
                    LazyRow(
                        contentPadding = PaddingValues(end = 16.dp) // Add padding to the end of the row
                    ) {
                        itemsIndexed(days) { index, (day, label) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(start = if (index == 0) 0.dp else 8.dp, end = 8.dp) // Adjust spacing between items
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (index == 0) Color.White else Color.Transparent)
                                        .border(
                                            width = if (index != 0) 1.dp else 0.dp,
                                            color = if (index != 0) Color(0xFF8E776B) else Color.Transparent,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (index == 0) Color(0xFF3B2B20) else Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = label,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Timeline Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Timeline",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3B2B20)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Newest",
                        fontSize = 14.sp,
                        color = Color(0xFF3B2B20)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Sort",
                        tint = Color(0xFF3B2B20)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Task List
            val tasks = listOf(
                Triple("10:00", "Watch Kotlin Basics On Youtube: Code With Harry", "Start by learning the basics"),
                Triple("14:00", "Set up Android Studio", "Make your First Project"),
                Triple("16:00", "Watch Kotlin Basics On Youtube: Code With Harry", "Start by learning the basic Language"),
                Triple("22:00", "Watch Kotlin Basics On Youtube: Code With Harry", "Start by learning the basic Language"),
            )

            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp), // Added bottom padding for FAB
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f) // Ensures LazyColumn takes available space
            ) {
                items(tasks) { (time, title, subtitle) ->
                    TaskCard(time, title, subtitle)
                }
            }
        } // Closing brace for the main Column

        // Floating Action Button
        FloatingActionButton(
            onClick = { /* Add task */ },
            containerColor = Color(0xFFAED982), // Use containerColor for M3 FAB
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp) // Adjust padding as needed
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }
    } // Closing brace for the main Box
} // Closing brace for DayWiseTasksScreen

@Composable
fun TaskCard(time: String, title: String, subtitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Time Badge
        Box(
            modifier = Modifier
                .padding(end = 8.dp) // Space between badge and card
                .background(Color(0xFF92B173), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = time,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        // Card Content
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White), // Correct way to set background for M3 Card
            modifier = Modifier.fillMaxWidth() // Card takes remaining width
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Pie-chart placeholder icon
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFDDD0C4))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF3B2B20),
                        modifier = Modifier.weight(1f).padding(end = 8.dp) // Allow text to take space, add padding
                    )
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFAED982), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("TIPS & TRICKS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White, maxLines = 1) // Added maxLines
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Videos • Avg. 15 Mins",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}