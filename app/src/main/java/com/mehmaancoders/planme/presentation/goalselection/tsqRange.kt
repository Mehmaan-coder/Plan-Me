package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
// Assuming Material 3 components for consistency, adjust if strictly Material 2
import androidx.compose.material3.Button // Material 3 Button
import androidx.compose.material3.ButtonDefaults // Material 3 ButtonDefaults
import androidx.compose.material3.Icon // Material 3 Icon
import androidx.compose.material3.Text // Material 3 Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Circle // Keep for the top bar icon if intended
// If the top bar 'Circle' is meant to be a back arrow, use:
// import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// import com.mehmaancoders.planme.R // Only if R.drawable.* or R.string.* are used directly

// Consider moving these colors to your app's theme (ui.theme/Color.kt)
val screenBackgroundRange = Color(0xFFFDF7F3)
val primaryTextRange = Color(0xFF3B2B20)
val iconTintRangeTopBar = Color(0xFF3B2B20) // For the top bar 'Circle' or 'ArrowBack'
val progressIndicatorBackgroundRange = Color(0xFFEADFD8)
val selectedRatingBubbleBg = Color(0xFFFF6B00)
val unselectedRatingBubbleBg = Color.Transparent // Or a very light gray like Color(0xFFF0F0F0)
val selectedRatingBubbleBorder = Color.White // Or Color.Transparent if no border needed when selected
val unselectedRatingBubbleBorder = Color.Transparent // Or a light border color
val selectedRatingTextRange = Color.White
val unselectedRatingTextRange = Color(0xFF3B2B20) // Same as primaryTextRange
val ratingBarBackground = Color.White
val descriptionTextRange = Color(0xFF6C5F58)
val buttonBackgroundRange = Color(0xFF3B2B20)
val buttonContentRange = Color.White


@Composable
@Preview(showSystemUi = true)
fun ExperienceLevelRatingScreen() {
    var selectedRating by remember { mutableStateOf(3) } // Default to a mid-range value, e.g., 3

    // More descriptive map keys might be useful if these strings are used elsewhere or for localization
    val experienceLevelDescriptions = mapOf(
        1 to "Beginner: Just starting out.",
        2 to "Novice: Some experience, learning the basics.",
        3 to "Intermediate: Comfortable, developing proficiency.",
        4 to "Advanced: Highly skilled, solid experience.",
        5 to "Expert: Deep understanding and mastery."
    )
    // Fallback description
    val defaultDescription = "Select a rating to see the description."

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundRange)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween // Pushes button to the bottom
    ) {
        Column(modifier = Modifier.weight(1f)) { // Main content takes available space
            Spacer(modifier = Modifier.height(32.dp))

            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                    // If this is a back button, add:
                    // .clickable { /* TODO: Handle back navigation */ }
                ) {
                    // If it's a back button, use Icons.Default.ArrowBack
                    Icon(
                        Icons.Default.Circle, // Original icon
                        contentDescription = "Assessment progress indicator", // Or "Back" if it's a back button
                        tint = iconTintRangeTopBar
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Assessment", fontWeight = FontWeight.Bold, color = primaryTextRange)
                }
                Box(
                    modifier = Modifier
                        .background(progressIndicatorBackgroundRange, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("3 of 4", fontSize = 12.sp, color = primaryTextRange)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "How would you rate your\nExperience level?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextRange
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Selected Rating Display
            Text(
                text = selectedRating.toString(),
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextRange,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Range bar with rounded background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .background(ratingBarBackground) // Use defined color
                    .padding(vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { value ->
                        val isSelected = selectedRating == value
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) selectedRatingBubbleBg else unselectedRatingBubbleBg)
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp, // Thicker border if selected, or adjust as needed
                                    color = if (isSelected) selectedRatingBubbleBorder else unselectedRatingBubbleBorder,
                                    shape = CircleShape
                                )
                                .clickable { selectedRating = value },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = value.toString(),
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) selectedRatingTextRange else unselectedRatingTextRange
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Dynamic description
            Text(
                text = experienceLevelDescriptions[selectedRating] ?: defaultDescription,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = descriptionTextRange,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } // End of weighted Column for main content

        // Continue Button
        Column(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)) { // Padding for spacing
            Button(
                onClick = { /* Handle next: selectedRating holds the value */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), // Height of the button itself
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundRange) // M3 uses containerColor
            ) {
                Text("Continue", color = buttonContentRange, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null, // Decorative as text describes action
                    tint = buttonContentRange
                )
            }
        }
    }
}