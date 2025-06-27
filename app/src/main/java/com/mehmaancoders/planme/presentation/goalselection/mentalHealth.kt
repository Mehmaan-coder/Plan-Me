package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // Import all filled icons for convenience
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip // Import for clip modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

// Consider moving these colors to your app's theme (ui.theme/Color.kt)
val screenBackgroundMood = Color(0xFFFDF7F3)
val primaryTextMood = Color(0xFF3B2B20)
val iconTintMood = Color(0xFF3B2B20)
val progressIndicatorBackgroundMood = Color(0xFFEADFD8)
val moodDescriptionColor = Color.Gray

// Specific mood colors - these could also be part of a theme or constants file
val sadMoodColor = Color(0xFFFF7A00)
val neutralMoodColor = Color(0xFFFFD338)
val happyMoodColor = Color(0xFFA7C879)

@Composable
@Preview(showSystemUi = true)
fun MoodSelectorScreen() {
    val moods = listOf("Sad", "Neutral", "Happy")
    val moodIcons = listOf("☹️", "😐", "😊")
    // Use the defined color constants
    val moodColors = listOf(sadMoodColor, neutralMoodColor, happyMoodColor)
    val descriptions = listOf("I feel Low.", "I Feel Neutral.", "I feel Great!")

    var selectedMoodIndex by remember { mutableStateOf(1) } // Default to Neutral

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundMood)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f) // Allow this column to take available space
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { /* TODO: Handle back navigation */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // Changed to a standard back icon
                        contentDescription = "Back", // If it's a functional back button
                        tint = iconTintMood
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Default Parameters",
                        color = primaryTextMood,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .background(progressIndicatorBackgroundMood, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("5 of 14", fontSize = 12.sp, color = primaryTextMood)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "How would you describe your mood lately?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextMood,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mood description
            Text(
                text = descriptions[selectedMoodIndex],
                fontSize = 16.sp,
                color = moodDescriptionColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Emoji Display
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(moodColors[selectedMoodIndex], CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = moodIcons[selectedMoodIndex],
                    fontSize = 48.sp // Ensure text is large enough for the emoji icon
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mood selection buttons (replaces simulated swipe)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp), // Keep consistent padding
                horizontalArrangement = Arrangement.SpaceEvenly, // Or Arrangement.SpaceAround
                verticalAlignment = Alignment.CenterVertically
            ) {
                moods.forEachIndexed { index, _ -> // Use mood from list if needed for label
                    val isSelected = index == selectedMoodIndex
                    IconButton(
                        onClick = { selectedMoodIndex = index },
                        modifier = Modifier.size(if (isSelected) 64.dp else 56.dp) // Slightly larger when selected
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize() // Fill IconButton size
                                .background(
                                    color = moodColors[index],
                                    shape = CircleShape
                                )
                                .border( // Add a border if selected for more visual feedback
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) primaryTextMood.copy(alpha = 0.5f) else Color.Transparent,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = moodIcons[index],
                                fontSize = if (isSelected) 30.sp else 24.sp // Slightly larger emoji when selected
                            )
                        }
                    }
                }
            }
        } // End of weighted column

        // Bottom "Continue" Button (Assuming this would be here, similar to other screens)
        // If the bottom progress bar is the only bottom element, this can be omitted.
        // For consistency with other screens, a Continue button is often expected.
        Column(modifier = Modifier.padding(bottom = 24.dp, top = 16.dp)) {
            Button(
                onClick = { /* TODO: Handle continue action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryTextMood), // Example color
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(text = "Continue", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null, // Decorative
                    tint = Color.White
                )
            }
        }

        // Bottom progress bar (fake swipe arc representation) - Kept if it's purely decorative
        // If this represents actual progress and isn't just decoration, consider a more standard progress indicator.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp) // Original height
                .padding(bottom = 16.dp), // Original padding
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(topStart = 60.dp))
                        .background(sadMoodColor), // Use defined color
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        // .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)) // This clip does nothing, can be removed
                        .background(neutralMoodColor), // Use defined color
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(topEnd = 60.dp))
                        .background(happyMoodColor), // Use defined color
                )
            }
        }
    }
}