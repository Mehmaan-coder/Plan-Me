package com.mehmaancoders.planme.presentation.progresstracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // Changed from LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskItem(date: String, title: String, description: String, progress: Int) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth() // Changed from .width(300.dp) for vertical list
        // elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // M3 elevation
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFF9F6F3)) // Consider theme color
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(12.dp)) // Consider theme color
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val parts = date.split(" ")
                val month = parts.firstOrNull()?.uppercase()?.take(3) ?: "N/A"
                val day = parts.getOrNull(1) ?: ""

                Text(text = month, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Text(text = day, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF3B2B20) // Consider theme color
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            val progressColor = if (progress >= 90) Color(0xFF7ABF4F) else Color(0xFFD0704C) // Consider theme colors
            CircularProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier.size(40.dp),
                strokeWidth = 5.dp,
                color = progressColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
fun TaskVerticalCarousel(tasks: List<Triple<String, String, Pair<String, Int>>>) { // Renamed for clarity
    LazyColumn( // Changed from LazyRow
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp), // Adjusted padding
        verticalArrangement = Arrangement.spacedBy(12.dp) // Changed from horizontalArrangement
    ) {
        items(tasks, key = { task -> task.second + task.first }) { task ->
            val (date, title, descProgress) = task
            val (description, progress) = descProgress
            TaskItem(date = date, title = title, description = description, progress = progress)
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ProgressTrackerScreen() {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Section with Background and Header
            Box(
                modifier = Modifier
                    .height(280.dp) // This height might need adjustment if the carousel below is very tall
                    .fillMaxWidth()
                    .background(Color(0xFF8855FF)) // Consider MaterialTheme.colorScheme.primary
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, start = 24.dp, end = 24.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Goal Tracker", // stringResource(R.string.goal_tracker)
                            style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                        )
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFCBD9A3), RoundedCornerShape(16.dp)) // Consider theme color
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "NORMAL", // stringResource(R.string.normal_status)
                                style = MaterialTheme.typography.labelSmall.copy(color = Color.White) // Ensure contrast
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "0%", // Should be dynamic state
                        style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    Text(
                        text = "Let's Get you Started", // stringResource(R.string.get_started_prompt)
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }

            // The Spacer below the header might need adjustment if the FAB is expected
            // to always overlap the carousel.
            // If the carousel itself is expected to scroll under the header,
            // the structure might need the carousel to be placed absolutely or the header
            // to be part of the LazyColumn's items.

            // For a simple vertical scroll, the FAB might be better placed at the bottom of the screen
            // or the content needs to be aware of the FAB's position.
            // Current FAB positioning will keep it over the top part of the screen.

            // Daily Progress Vertical Carousel
            // Spacer(modifier = Modifier.height(60.dp)) // This spacer might push content too far down if FAB is not overlapping this area

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Allow the carousel to take available vertical space
                // .padding(top = 32.dp) // Add padding if FAB is overlapping and you want content below it
            ) {
                Text(
                    text = "Daily Progress", // stringResource(R.string.daily_progress)
                    style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF3B2B20)), // Use theme onSurface color
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp) // Adjust padding as needed
                )
                Spacer(modifier = Modifier.height(16.dp))

                val tasks = remember {
                    listOf(
                        Triple("Sep 12", "Plan out your App", "List your App’s Core functions" to 65),
                        Triple("Sep 11", "Create your first app", "Hands-on with Android Studio" to 95),
                        Triple("Sep 11", "Learn the basics", "Take a revision test" to 65),
                        Triple("Sep 10", "Setup Environment", "Install Android Studio" to 100),
                        Triple("Sep 09", "Brainstorm Ideas", "Think of cool app features" to 30),
                        // Add more items to test vertical scrolling
                        Triple("Sep 08", "Another Task", "Description for another task" to 50),
                        Triple("Sep 07", "Yet Another Task", "More details here" to 75),
                    )
                }

                TaskVerticalCarousel(tasks = tasks) // Use the renamed carousel
            }
        }

        // Floating Add Button - Consider its placement with a vertical scrolling list.
        // If the list is long, this FAB might remain fixed while content scrolls under it.
        // Or it might be better placed at the bottom if it relates to the whole screen.
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (280.dp - 32.dp)) // Keeps FAB at the bottom of the header
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = { /* Handle add action */ },
                shape = CircleShape,
                containerColor = Color(0xFF3B2B20), // Consider MaterialTheme.colorScheme.primary or secondary
                contentColor = Color.White // Consider MaterialTheme.colorScheme.onPrimary or onSecondary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Task" // stringResource(R.string.add_task_description)
                )
            }
        }
    }
}