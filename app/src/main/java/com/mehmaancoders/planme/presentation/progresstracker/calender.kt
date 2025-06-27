package com.mehmaancoders.planme.presentation.progresstracker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // Using Material 3 components
import androidx.compose.material.icons.Icons // Correct import for M3 Icons
import androidx.compose.material.icons.filled.History // Correct import for M3 Icons
import androidx.compose.material.icons.filled.MoreVert // Correct import for M3 Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// import com.mehmaancoders.planme.R // Not used in this snippet

@Composable
@Preview(showSystemUi = true)
fun HealthJournalScreen() {
    // Define colors (Consider moving to a Theme)
    val backgroundColor = Color(0xFFA66A47)
    // val neutralColor = Color(0xFF8A5032) // Defined but not directly used in HealthJournalScreen, passed to LegendDot
    // val positiveColor = Color(0xFFA5C462) // Defined but not directly used in HealthJournalScreen, passed to LegendDot
    // val negativeColor = Color(0xFFFF823C) // Defined but not directly used in HealthJournalScreen, passed to LegendDot
    // val fadedNeutral = Color(0xFFE6D0C5) // Defined but not directly used in HealthJournalScreen, used in DotGrid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Top Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Using IconButton from Material 3
            IconButton(onClick = { /* TODO: Handle history click */ }) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "View History", // Added content description
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Using Text from Material 3
            Text(
                text = "Health Journal",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Days Counter
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "34/365",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 48.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Successful Days this year. Keep it Up!",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }

        // Overlapping button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp), // This might be a bit large, pushing content down
            contentAlignment = Alignment.Center
        ) {
            // Consider making this a FloatingActionButton (FAB) or a styled Button
            // for better semantics and accessibility if it's a primary action.
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4D2D1F))
                // .clickable { /* TODO: Handle add action */ } // If this should be clickable
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "+",
                    color = Color.White,
                    fontSize = 32.sp
                    // Consider contentDescription for this Text if it's conveying an action
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp)) // Increased spacing might be intended

        // Bottom White Section
        // Using Surface from Material 3
        Surface(
            color = Color.White, // Surface color is distinct from theme background
            shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp),
            modifier = Modifier.fillMaxSize() // This will take all remaining space
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Productivity History",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5C3924),
                        modifier = Modifier.weight(1f)
                    )
                    // Consider making this an IconButton if it's interactive
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options", // Added content description
                        tint = Color.Gray
                        // .clickable { /* TODO: Handle more options click */ } // If interactive
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Calendar-like Dot Grid
                DotGrid() // Calling the DotGrid composable

                Spacer(modifier = Modifier.height(24.dp))

                // Legend
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color(0xFFF8F0EC))
                        .padding(vertical = 12.dp, horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Directly using defined colors, which is okay if they are local to this screen concept
                    LegendDot(Color(0xFFFF823C), "Negative") // negativeColor
                    LegendDot(Color(0xFF8A5032), "Neutral")   // neutralColor
                    LegendDot(Color(0xFFA5C462), "Positive")  // positiveColor
                }
            }
        }
    }
}

@Composable
fun DotGrid() {
    // These colors are specific to the DotGrid's current state/data.
    // In a real app, this data would likely come from a ViewModel or state.
    val colors = listOf(
        listOf(Color.Transparent, Color(0xFFE6D0C5), Color(0xFFE6D0C5), Color(0xFFE6D0C5), Color(0xFFE6D0C5), Color(0xFFE6D0C5)),
        listOf(Color(0xFFE6D0C5), Color(0xFF8A5032), Color(0xFFA5C462), Color(0xFFA5C462), Color(0xFFFF823C), Color.Transparent),
        listOf(Color.Transparent, Color.Transparent, Color.Transparent, Color(0xFFA5C462), Color(0xFF8A5032), Color(0xFF8A5032)),
        listOf(Color(0xFFE6D0C5), Color(0xFFE6D0C5), Color(0xFFE6D0C5), Color.Transparent, Color(0xFFE6D0C5), Color(0xFFE6D0C5))
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp) // Spacing between rows of dots
    ) {
        colors.forEach { rowColors ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Distributes dots evenly
            ) {
                rowColors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(24.dp) // Size of each dot
                            .clip(CircleShape)
                            .border( // Border logic
                                width = if (color == Color.Transparent) 1.dp else 0.dp,
                                color = Color(0xFFE6D0C5), // fadedNeutral effectively
                                shape = CircleShape
                            )
                            .background(color) // Background color of the dot
                    )
                }
            }
        }
    }
}

@Composable
fun LegendDot(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF5C3924) // A specific brown color for legend text
        )
    }
}