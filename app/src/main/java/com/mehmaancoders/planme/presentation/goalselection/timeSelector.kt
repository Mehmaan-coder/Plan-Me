package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import kotlinx.coroutines.launch

// Consider moving these colors to your app's theme (ui.theme/Color.kt)
val screenBackground = Color(0xFFFDF7F3)
val primaryText = Color(0xFF3B2B20)
val iconTint = Color(0xFF3B2B20)
val progressIndicatorBackground = Color(0xFFEADFD8)
val selectedTimeBackground = Color(0xFF9CBF60)
val unselectedTimeColor = Color.Gray
val selectedTimeText = Color.White
val deadlineTextColor = Color(0xFFFF6600)
val buttonBackground = Color(0xFF3B2B20)
val buttonContent = Color.White

@Composable
@Preview(showSystemUi = true)
fun StyledTimeSelectorScreen() {
    var selectedHour by remember { mutableStateOf(18) } // Initial selection
    val timeOptions = (16..20).toList() // Example time options

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Center the initial selected item
    LaunchedEffect(Unit) {
        val initialIndex = timeOptions.indexOf(selectedHour)
        if (initialIndex != -1) {
            // Calculate offset to center the item. This might need adjustment based on item size.
            // For simplicity, this scrolls to the item. Precise centering needs item height.
            lazyListState.scrollToItem(initialIndex)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackground) // Use defined color
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) { // Allow this column to take available space
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
                        imageVector = Icons.Default.ArrowBack, // Standard back icon
                        contentDescription = "Back",
                        tint = iconTint
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Default Parameters",
                        color = primaryText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .background(progressIndicatorBackground, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("3 of 14", fontSize = 12.sp, color = primaryText)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "How much time can you dedicate to this goal?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText,
                modifier = Modifier.padding(bottom = 16.dp) // Adjusted padding
            )

            // Time Scroll Picker and Labels
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Define a height for the picker area
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Time Scroll Picker
                Box(
                    modifier = Modifier.weight(1f), // Allow LazyColumn to take available width
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        state = lazyListState,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center, // Center items vertically in the column
                        modifier = Modifier.fillMaxHeight(), // Fill the height of the parent Row
                        contentPadding = PaddingValues(vertical = 16.dp) // Padding for scrollable area
                    ) {
                        items(count = timeOptions.size, key = { timeOptions[it] }) { index ->
                            val hour = timeOptions[index]
                            val isSelected = hour == selectedHour
                            val itemSize = if (isSelected) 80.dp else 50.dp // Adjusted sizes
                            val fontSize = if (isSelected) 30.sp else 18.sp

                            Box(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .size(itemSize)
                                    .background(
                                        if (isSelected) selectedTimeBackground else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        selectedHour = hour
                                        // Scroll to the selected item smoothly
                                        coroutineScope.launch {
                                            lazyListState.animateScrollToItem(index)
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = hour.toString(),
                                    fontSize = fontSize,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) selectedTimeText else unselectedTimeColor
                                )
                            }
                        }
                    }
                }

                // Spacer to create some distance if needed, or adjust padding
                Spacer(modifier = Modifier.width(16.dp))

                // Right-aligned labels
                Column(
                    modifier = Modifier.padding(end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center // Center labels vertically
                ) {
                    Text(
                        "Hours/\nDay",
                        color = unselectedTimeColor, // Use a consistent color
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Box(
                        modifier = Modifier
                            .background(selectedTimeBackground, RoundedCornerShape(20.dp)) // Use consistent shape/color
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            "Days/\nWeek",
                            color = selectedTimeText, // Use consistent color
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Set a deadline link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Short on time? ", color = unselectedTimeColor)
                Text(
                    text = "Set a deadline.",
                    color = deadlineTextColor, // Use defined color
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* TODO: Handle deadline click */ }
                )
            }
        } // End of weighted column

        // Continue Button
        Column(modifier = Modifier.padding(bottom = 24.dp, top = 16.dp)) { // Ensure space from content above
            Button(
                onClick = { /* TODO: Handle continue */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonBackground), // Use defined color
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(text = "Continue", color = buttonContent, fontWeight = FontWeight.Bold) // Use defined color
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null, // Decorative
                    tint = buttonContent // Use defined color
                )
            }
        }
    }
}