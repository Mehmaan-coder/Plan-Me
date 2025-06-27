package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

// It's good practice to define your colors in your theme
// e.g., in ui.theme.Color.kt and then use MaterialTheme.colors.surface
val screenBackgroundColorBudget = Color(0xFFFDF7F3)
val primaryTextColorBudget = Color(0xFF3B2B20)
val iconTintColorBudget = Color(0xFF3B2B20)
val progressIndicatorBackgroundColorBudget = Color(0xFFEADFD8)
val currencySelectorSelectedBg = Color(0xFFFF7A00)
val currencySelectorUnselectedBg = Color.Transparent
val currencySelectorSelectedText = Color.White
val budgetValueKTextColor = Color.Gray
val sliderThumbColorBudget = Color(0xFF9CBF60)
val sliderActiveTrackColorBudget = Color(0xFFDAD0C9)
val sliderInactiveTrackColorBudget = Color(0xFFE8DAD2)
val buttonBackgroundColorBudget = Color(0xFF3B2B20)
val buttonContentColorBudget = Color.White


@Composable
@Preview(showSystemUi = true)
fun BudgetSelectorScreen() {
    var selectedCurrency by remember { mutableStateOf("INR") }
    var budgetValue by remember { mutableStateOf(10.1f) } // Default to 10.1k

    val currencies = listOf("INR", "Dollar", "Pound")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundColorBudget)
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
                        imageVector = Icons.Default.ArrowBack, // Changed to a standard back icon
                        contentDescription = "Back", // If it's a functional back button
                        tint = iconTintColorBudget
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Default Parameters",
                        color = primaryTextColorBudget,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            progressIndicatorBackgroundColorBudget,
                            RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("4 of 14", fontSize = 12.sp, color = primaryTextColorBudget)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "What’s your Budget ?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextColorBudget
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Currency Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(40.dp)) // Clip the outer Row
                    .background(Color.White) // Background for the whole selector
                    .padding(4.dp), // Padding inside the white background
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                currencies.forEach { currency ->
                    val isSelected = currency == selectedCurrency
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(36.dp)) // Clip individual items
                            .background(if (isSelected) currencySelectorSelectedBg else currencySelectorUnselectedBg)
                            .clickable { selectedCurrency = currency }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currency,
                            color = if (isSelected) currencySelectorSelectedText else primaryTextColorBudget,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Budget Value Display
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom // Align "k" to the bottom of the number
            ) {
                Text(
                    text = "%.1f".format(budgetValue), // Format to one decimal place
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryTextColorBudget
                )
                Text(
                    text = "k",
                    fontSize = 32.sp, // "k" is smaller
                    fontWeight = FontWeight.Bold,
                    color = budgetValueKTextColor, // Use a distinct color for "k"
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp) // Adjust padding for visual alignment
                )
            }

            // Budget Slider
            Slider(
                value = budgetValue,
                onValueChange = { budgetValue = it },
                valueRange = 9f..11f, // The range is 9k to 11k
                steps = 19, // (11 - 9) / 0.1 - 1 = 2 / 0.1 - 1 = 20 - 1 = 19 steps for 20 distinct values if step is 0.1
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = sliderThumbColorBudget,
                    activeTrackColor = sliderActiveTrackColorBudget,
                    inactiveTrackColor = sliderInactiveTrackColorBudget
                )
            )
        } // End of weighted column

        // Continue Button
        Column(modifier = Modifier.padding(bottom = 24.dp, top = 16.dp)) { // Ensure space from content above
            Button(
                onClick = { /* TODO: Handle continue */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), // Height of the button itself
                colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColorBudget),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(text = "Continue", color = buttonContentColorBudget, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null, // Decorative icon
                    tint = buttonContentColorBudget
                )
            }
        }
    }
}