package com.mehmaancoders.planme.presentation.goalselection

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.presentation.navigation.Routes
import kotlin.math.roundToInt

// Theme Colors
val screenBackgroundMood = Color(0xFFFDF7F3)
val primaryTextMood = Color(0xFF3B2B20)
val iconTintMood = Color(0xFF3B2B20)
val progressIndicatorBackgroundMood = Color(0xFFEADFD8)
val moodDescriptionColor = Color.Gray

// Extended Mood Colors
val depressedColor = Color(0xFF1A1A2E)
val sadColor = Color(0xFF16537E)
val disappointedColor = Color(0xFF0F3460)
val worriedColor = Color(0xFF533A71)
val stressedColor = Color(0xFF7209B7)
val overwhelmedColor = Color(0xFF560A86)
val neutralColor = Color(0xFF6C7B7F)
val calmColor = Color(0xFF82AAE3)
val contentColor = Color(0xFF91C8E4)
val optimisticColor = Color(0xFFA8E6CF)
val happyColor = Color(0xFFB8E6B8)
val joyfulColor = Color(0xFFC7F9CC)
val excitedColor = Color(0xFFFFE66D)
val energeticColor = Color(0xFFFF8E53)
val euphoricColor = Color(0xFFFF6B6B)

data class MoodData(
    val id: Int,
    val name: String,
    val emoji: String,
    val color: Color,
    val description: String,
    val intensity: Float, // 0.0 (very negative) to 1.0 (very positive)
    val category: MoodCategory
)

enum class MoodCategory {
    VERY_NEGATIVE,
    NEGATIVE,
    SLIGHTLY_NEGATIVE,
    NEUTRAL,
    SLIGHTLY_POSITIVE,
    POSITIVE,
    VERY_POSITIVE
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
@Preview(showSystemUi = true)
fun MoodSelectorScreen(
    navHostController: NavHostController? = null,
    onMoodSelected: (MoodData) -> Unit = {},
    onContinue: (MoodData) -> Unit = {},
    onBack: () -> Unit = {}
) {
    // Comprehensive mood spectrum from very negative to very positive
    val allMoods = listOf(
        // Very Negative (0.0 - 0.15)
        MoodData(1, "Depressed", "😞", depressedColor, "I feel deeply sad and hopeless.", 0.0f, MoodCategory.VERY_NEGATIVE),
        MoodData(2, "Devastated", "😭", Color(0xFF0D1B2A), "I feel completely broken down.", 0.05f, MoodCategory.VERY_NEGATIVE),
        MoodData(3, "Miserable", "😖", Color(0xFF1B263B), "I feel extremely unhappy.", 0.1f, MoodCategory.VERY_NEGATIVE),

        // Negative (0.15 - 0.35)
        MoodData(4, "Sad", "😢", sadColor, "I feel down and melancholic.", 0.15f, MoodCategory.NEGATIVE),
        MoodData(5, "Disappointed", "😔", disappointedColor, "Things didn't go as expected.", 0.2f, MoodCategory.NEGATIVE),
        MoodData(6, "Frustrated", "😤", Color(0xFFE63946), "I feel annoyed and blocked.", 0.25f, MoodCategory.NEGATIVE),
        MoodData(7, "Angry", "😠", Color(0xFFDC2F02), "I feel intense irritation.", 0.3f, MoodCategory.NEGATIVE),

        // Slightly Negative (0.35 - 0.45)
        MoodData(8, "Worried", "😟", worriedColor, "I feel anxious about something.", 0.35f, MoodCategory.SLIGHTLY_NEGATIVE),
        MoodData(9, "Stressed", "😰", stressedColor, "I feel under pressure.", 0.4f, MoodCategory.SLIGHTLY_NEGATIVE),
        MoodData(10, "Overwhelmed", "🤯", stressedColor, "I have too much to handle.", 0.43f, MoodCategory.SLIGHTLY_NEGATIVE),

        // Neutral (0.45 - 0.55)
        MoodData(11, "Neutral", "😐", neutralColor, "I feel balanced and steady.", 0.5f, MoodCategory.NEUTRAL),
        MoodData(12, "Tired", "😴", Color(0xFF8D99AE), "I feel low on energy.", 0.48f, MoodCategory.NEUTRAL),
        MoodData(13, "Bored", "😑", Color(0xFF9B9B9B), "I lack interest or excitement.", 0.52f, MoodCategory.NEUTRAL),

        // Slightly Positive (0.55 - 0.65)
        MoodData(14, "Calm", "😌", calmColor, "I feel peaceful and relaxed.", 0.55f, MoodCategory.SLIGHTLY_POSITIVE),
        MoodData(15, "Content", "🙂", contentColor, "I feel satisfied with things.", 0.6f, MoodCategory.SLIGHTLY_POSITIVE),
        MoodData(16, "Hopeful", "🤞", Color(0xFF7FB3D3), "I feel positive about the future.", 0.63f, MoodCategory.SLIGHTLY_POSITIVE),

        // Positive (0.65 - 0.85)
        MoodData(17, "Happy", "😊", happyColor, "I feel good and cheerful.", 0.65f, MoodCategory.POSITIVE),
        MoodData(18, "Grateful", "🙏", Color(0xFFB5E48C), "I feel thankful and appreciative.", 0.7f, MoodCategory.POSITIVE),
        MoodData(19, "Optimistic", "😄", optimisticColor, "I feel positive and confident.", 0.75f, MoodCategory.POSITIVE),
        MoodData(20, "Joyful", "😁", joyfulColor, "I feel filled with happiness.", 0.8f, MoodCategory.POSITIVE),

        // Very Positive (0.85 - 1.0)
        MoodData(21, "Excited", "🤩", excitedColor, "I feel thrilled and enthusiastic!", 0.85f, MoodCategory.VERY_POSITIVE),
        MoodData(22, "Energetic", "⚡", energeticColor, "I feel full of energy and vitality!", 0.9f, MoodCategory.VERY_POSITIVE),
        MoodData(23, "Euphoric", "🥳", euphoricColor, "I feel absolutely amazing!", 0.95f, MoodCategory.VERY_POSITIVE),
        MoodData(24, "Blissful", "😇", Color(0xFFFFD23F), "I feel pure joy and peace!", 1.0f, MoodCategory.VERY_POSITIVE)
    )

    var selectedMoodIndex by remember { mutableStateOf(10) } // Default to Neutral
    var dragProgress by remember { mutableStateOf(0.5f) } // 0.0 to 1.0 across the spectrum
    var isDragging by remember { mutableStateOf(false) }
    var currentStep by remember { mutableStateOf(5) }

    val currentMood = allMoods[selectedMoodIndex]

    // Update mood selection based on drag progress
    LaunchedEffect(dragProgress) {
        // Map drag progress to mood index
        val moodIndex = (dragProgress * (allMoods.size - 1)).roundToInt()
            .coerceIn(0, allMoods.size - 1)

        if (moodIndex != selectedMoodIndex) {
            selectedMoodIndex = moodIndex
            onMoodSelected(allMoods[moodIndex])
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundMood)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Top Navigation Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        navHostController?.navigate(Routes.BudgetSelectorScreen) ?: onBack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = iconTintMood
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Mood Assessment",
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
                    Text("4 of 5", fontSize = 12.sp, color = primaryTextMood)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Question Title
            Text(
                text = "How would you describe your mood lately?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextMood,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Current Mood Name
            Text(
                text = currentMood.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = currentMood.color,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Current Mood Description
            Text(
                text = currentMood.description,
                fontSize = 16.sp,
                color = moodDescriptionColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Large Mood Emoji Display with Animation
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(
                        color = currentMood.color.copy(alpha = 0.15f),
                        shape = CircleShape
                    )
                    .border(
                        width = 3.dp,
                        color = currentMood.color.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
                    .clickable {
                        // Cycle through nearby moods
                        val nextIndex = (selectedMoodIndex + 1) % allMoods.size
                        selectedMoodIndex = nextIndex
                        dragProgress = nextIndex.toFloat() / (allMoods.size - 1)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentMood.emoji,
                    fontSize = 64.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Mood Category Indicator
            Box(
                modifier = Modifier
                    .background(
                        currentMood.color.copy(alpha = 0.1f),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = when (currentMood.category) {
                        MoodCategory.VERY_NEGATIVE -> "Very Low"
                        MoodCategory.NEGATIVE -> "Low"
                        MoodCategory.SLIGHTLY_NEGATIVE -> "Slightly Low"
                        MoodCategory.NEUTRAL -> "Balanced"
                        MoodCategory.SLIGHTLY_POSITIVE -> "Slightly High"
                        MoodCategory.POSITIVE -> "High"
                        MoodCategory.VERY_POSITIVE -> "Very High"
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = currentMood.color
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Swipe Indicator
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Swipe to explore moods",
                    tint = Color.Gray.copy(alpha = 0.6f),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // Bottom Interactive Mood Spectrum
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Mood Spectrum Visualization
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                val containerWidth = maxWidth

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { isDragging = true },
                                onDragEnd = { isDragging = false }
                            ) { change, _ ->
                                val width = size.width.toFloat()
                                val touchX = change.position.x.coerceIn(0f, width)
                                dragProgress = (touchX / width).coerceIn(0f, 1f)
                            }
                        }
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val width = size.width.toFloat()
                                val clickX = offset.x.coerceIn(0f, width)
                                dragProgress = (clickX / width).coerceIn(0f, 1f)
                            }
                        }

                ) {
                    drawMoodSpectrum(
                        moods = allMoods,
                        selectedMood = currentMood,
                        dragProgress = dragProgress,
                        isDragging = isDragging
                    )
                }

                // Interactive Handle
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(
                            x = containerWidth * dragProgress - 16.dp
                        )
                        .size(32.dp)
                        .background(Color.White, CircleShape)
                        .border(
                            width = 3.dp,
                            color = if (isDragging) currentMood.color else primaryTextMood,
                            shape = CircleShape
                        )
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { isDragging = true },
                                onDragEnd = { isDragging = false }
                            ) { _, dragAmount ->
                                val width = size.width.toFloat()
                                val deltaProgress = dragAmount.x / width
                                dragProgress = (dragProgress + deltaProgress).coerceIn(0f, 1f)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                if (isDragging) currentMood.color else primaryTextMood,
                                CircleShape
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Mood Shortcuts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val quickMoods = listOf(
                    allMoods[3], // Sad
                    allMoods[10], // Neutral
                    allMoods[16], // Happy
                    allMoods[20] // Excited
                )

                quickMoods.forEach { mood ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            selectedMoodIndex = allMoods.indexOf(mood)
                            dragProgress = selectedMoodIndex.toFloat() / (allMoods.size - 1)
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    mood.color.copy(alpha = if (mood == currentMood) 0.3f else 0.1f),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = mood.emoji, fontSize = 20.sp)
                        }
                        Text(
                            text = mood.name,
                            fontSize = 10.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Continue Button
            Button(
                onClick = {
                    onContinue(currentMood)
                    navHostController?.navigate(Routes.ExperienceLevelRatingScreen)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = currentMood.color),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Continue with ${currentMood.name}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private fun DrawScope.drawMoodSpectrum(
    moods: List<MoodData>,
    selectedMood: MoodData,
    dragProgress: Float,
    isDragging: Boolean
) {
    val width = size.width
    val height = size.height
    val segmentWidth = width / moods.size

    // Draw gradient background representing mood spectrum
    for (i in moods.indices) {
        val startX = i * segmentWidth
        val color = if (moods[i] == selectedMood) {
            moods[i].color.copy(alpha = 0.8f)
        } else {
            moods[i].color.copy(alpha = 0.4f)
        }

        drawRect(
            color = color,
            topLeft = Offset(startX, height * 0.2f),
            size = Size(segmentWidth, height * 0.6f)
        )
    }

    // Draw selected mood indicator
    val selectedX = dragProgress * width
    drawCircle(
        color = selectedMood.color,
        radius = 6.dp.toPx(),
        center = Offset(selectedX, height / 2)
    )
}