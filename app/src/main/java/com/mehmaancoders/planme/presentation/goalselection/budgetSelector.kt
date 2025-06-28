package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import kotlin.math.roundToInt
import kotlin.math.log10
import kotlin.math.pow
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

// Theme Colors
val screenBackgroundColor = Color(0xFFFDF7F3)
val primaryTextColor = Color(0xFF3B2B20)
val iconTintColor = Color(0xFF3B2B20)
val progressIndicatorBackgroundColor = Color(0xFFEADFD8)
val currencySelectorSelectedBg = Color(0xFFFF7A00)
val currencySelectorUnselectedBg = Color.Transparent
val currencySelectorSelectedText = Color.White
val budgetValueKTextColor = Color(0xFF8B7355)
val sliderThumbColor = Color(0xFF9CBF60)
val sliderActiveTrackColor = Color(0xFFDAD0C9)
val sliderInactiveTrackColor = Color(0xFFE8DAD2)
val buttonBackgroundColor = Color(0xFF3B2B20)
val buttonContentColor = Color.White

@Composable
fun BudgetSelectorScreen(navHostController: NavHostController) {
    var selectedCurrency by remember { mutableStateOf("INR") }
    var budgetValue by remember { mutableStateOf(10.0f) }

    val currencies = listOf("INR", "Dollar", "Pound")

    // Budget range: 1k to 100k (1 lakh)
    val minBudget = 1f
    val maxBudget = 100f

    // Function to format budget display
    fun formatBudgetValue(value: Float): String {
        return when {
            value < 10 -> "%.1f".format(value)
            value < 100 -> "%.0f".format(value)
            else -> "%.0f".format(value)
        }
    }

    // Function to get scale markers
    fun getScaleMarkers(): List<Float> {
        return listOf(1f, 5f, 10f, 25f, 50f, 100f)
    }

    // Function to convert linear slider position to logarithmic budget value
    fun sliderToBudget(sliderValue: Float): Float {
        // Use logarithmic scale for better distribution
        val logMin = log10(minBudget.toDouble())
        val logMax = log10(maxBudget.toDouble())
        val logValue = logMin + sliderValue * (logMax - logMin)
        return 10.0.pow(logValue).toFloat()
    }

    // Function to convert budget value to slider position
    fun budgetToSlider(budgetValue: Float): Float {
        val logMin = log10(minBudget.toDouble())
        val logMax = log10(maxBudget.toDouble())
        val logValue = log10(budgetValue.toDouble())
        return ((logValue - logMin) / (logMax - logMin)).toFloat()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundColor)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            // Status Bar Spacer
            Spacer(modifier = Modifier.height(8.dp))

            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = primaryTextColor, shape = CircleShape)
                        .clickable { navHostController.navigate(Routes.TimeSelectorScreen) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.planme_back),
                        contentDescription = "Back",
                        tint = iconTintColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Title
                Text(
                    text = "Default Parameters",
                    color = primaryTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                // Progress Indicator
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(progressIndicatorBackgroundColor)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "3 of 5",
                        fontSize = 12.sp,
                        color = primaryTextColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Main Question Title
            Text(
                text = "What's your Budget ?",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextColor,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Currency Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.White)
                    .padding(4.dp)
            ) {
                currencies.forEach { currency ->
                    val isSelected = currency == selectedCurrency
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(36.dp))
                            .background(if (isSelected) currencySelectorSelectedBg else currencySelectorUnselectedBg)
                            .clickable { selectedCurrency = currency }
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currency,
                            color = if (isSelected) currencySelectorSelectedText else primaryTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Budget Value Display
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = formatBudgetValue(budgetValue),
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryTextColor
                )
                Text(
                    text = "k",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = budgetValueKTextColor,
                    modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Custom Slider with Scale and Dots
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Canvas(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val trackY = size.height / 2 - 12.dp.toPx()
                        val trackStartX = 24.dp.toPx()
                        val trackEndX = size.width - 24.dp.toPx()
                        val trackWidth = trackEndX - trackStartX

                        // Draw main track line
                        drawLine(
                            color = Color(0xFFDAD0C9),
                            start = androidx.compose.ui.geometry.Offset(trackStartX, trackY),
                            end = androidx.compose.ui.geometry.Offset(trackEndX, trackY),
                            strokeWidth = 2.dp.toPx()
                        )

                        // Draw dots at regular intervals
                        val dotCount = 21 // 21 dots for better granularity
                        for (i in 0 until dotCount) {
                            val dotProgress = i.toFloat() / (dotCount - 1)
                            val dotX = trackStartX + trackWidth * dotProgress

                            // Determine if this dot should be active (green) or inactive (gray)
                            val sliderProgress = budgetToSlider(budgetValue)
                            val isActive = dotProgress <= sliderProgress

                            val dotColor = if (isActive) Color(0xFF9CBF60) else Color(0xFFDAD0C9)
                            val dotRadius = 3.dp.toPx()

                            drawCircle(
                                color = dotColor,
                                radius = dotRadius,
                                center = androidx.compose.ui.geometry.Offset(dotX, trackY)
                            )
                        }

                        // Draw the main slider thumb
                        val thumbProgress = budgetToSlider(budgetValue)
                        val thumbX = trackStartX + trackWidth * thumbProgress

                        // Outer thumb circle (larger, green)
                        drawCircle(
                            color = Color(0xFF9CBF60),
                            radius = 14.dp.toPx(),
                            center = androidx.compose.ui.geometry.Offset(thumbX, trackY)
                        )

                        // Inner thumb circle (smaller, white)
                        drawCircle(
                            color = Color.White,
                            radius = 8.dp.toPx(),
                            center = androidx.compose.ui.geometry.Offset(thumbX, trackY)
                        )
                    }

                    // Invisible slider for touch interaction
                    Slider(
                        value = budgetToSlider(budgetValue),
                        onValueChange = { sliderValue ->
                            budgetValue = sliderToBudget(sliderValue)
                        },
                        valueRange = 0f..1f,
                        steps = 98, // More steps for finer control
                        modifier = Modifier.fillMaxSize(),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Transparent,
                            activeTrackColor = Color.Transparent,
                            inactiveTrackColor = Color.Transparent,
                            disabledThumbColor = Color.Transparent,
                            disabledActiveTrackColor = Color.Transparent,
                            disabledInactiveTrackColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Scale markers below slider
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    getScaleMarkers().forEach { value ->
                        Text(
                            text = when {
                                value < 10 -> "%.0f".format(value)
                                value == 100f -> "1L"
                                else -> "%.0f".format(value)
                            },
                            fontSize = 12.sp,
                            color = budgetValueKTextColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))
        }

        // Continue Button
        Button(
            onClick = { navHostController.navigate(Routes.MoodSelectorScreen) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Continue",
                color = buttonContentColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = buttonContentColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}