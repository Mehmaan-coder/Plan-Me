package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

// Color definitions
val screenBackgroundRange = Color(0xFFFDF7F3)
val primaryTextRange = Color(0xFF3B2B20)
val iconTintRangeTopBar = Color(0xFF3B2B20)
val progressIndicatorBackgroundRange = Color(0xFFEADFD8)
val selectedRatingBubbleBg = Color(0xFFFF6B00)
val unselectedRatingBubbleBg = Color.White
val selectedRatingBubbleBorder = Color.Transparent
val unselectedRatingBubbleBorder = Color.Transparent
val selectedRatingTextRange = Color.White
val unselectedRatingTextRange = Color(0xFF3B2B20)
val descriptionTextRange = Color(0xFF6C5F58)
val buttonBackgroundRange = Color(0xFF3B2B20)
val buttonContentRange = Color.White

@Composable
fun ExperienceLevelRatingScreen(navHostController: NavHostController) {
    var selectedRating by remember { mutableStateOf(5) } // Default to 5 as shown in image

    // Updated experience level descriptions with motivational quotes
    val experienceLevelDescriptions = mapOf(
        1 to "You're just getting started, but that's perfectly fine!",
        2 to "You're learning and making progress, keep it up!",
        3 to "You're doing well and building confidence!",
        4 to "You're performing great and really skilled!",
        5 to "You Are Extremely Stressed Out."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundRange)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(32.dp))

            // Top bar with back arrow
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { navHostController.navigate(Routes.MoodSelectorScreen) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .border(1.5.dp, primaryTextColor, CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.planme_back),
                            contentDescription = "Back",
                            tint = primaryTextColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Assessment",
                        fontWeight = FontWeight.Bold,
                        color = primaryTextRange,
                        fontSize = 16.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .background(progressIndicatorBackgroundRange, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("5 of 5", fontSize = 12.sp, color = primaryTextRange)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "How would you rate your\nExperience level?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextRange,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Selected Rating Display
            Text(
                text = selectedRating.toString(),
                fontSize = 120.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextRange,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Rating buttons in horizontal row with white circular backgrounds
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
                            .clickable { selectedRating = value },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = value.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = if (isSelected) selectedRatingTextRange else unselectedRatingTextRange
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Dynamic description
            Text(
                text = experienceLevelDescriptions[selectedRating] ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = descriptionTextRange,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Continue Button
        Column(modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)) {
            Button(
                onClick = {
                    navHostController.navigate(Routes.FetchingScreen)
                    println("Selected rating: $selectedRating")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundRange)
            ) {
                Text(
                    "Continue",
                    color = buttonContentRange,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = buttonContentRange
                )
            }
        }
    }
}