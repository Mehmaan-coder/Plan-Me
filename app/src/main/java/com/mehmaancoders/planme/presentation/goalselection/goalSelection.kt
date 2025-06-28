package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.presentation.navigation.Routes
import com.mehmaancoders.planme.R

@Composable
fun GoalSelectionScreen(navHostController: NavHostController) {
    var selectedOption by remember { mutableStateOf("Create an App") }
    var customGoalText by remember { mutableStateOf("") }

    val options = listOf(
        "Watch Imdb Top 100" to Icons.Default.FavoriteBorder,
        "Create an App" to Icons.Default.Android,
        "Learn how to do a Cartwheel" to Icons.Default.Flag,
        "Quit Smoking" to Icons.Default.SentimentSatisfied,
        "Other" to Icons.Default.MoreHoriz
    )

    // Colors matching your PNG/Figma
    val backgroundColor = Color(0xFFFDF7F3)
    val primaryTextColor = Color(0xFF3B2B20)
    val accentColor = Color(0xFFA7C879)
    val secondaryBackgroundColor = Color(0xFFEADFD8)
    val buttonBackgroundColor = Color(0xFF3B2B20)
    val selectedIconColor = Color.White
    val unselectedIconColor = Color.Gray
    val selectedRadioColor = Color.White
    val unselectedRadioColor = Color(0xFF3B2B20)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {navHostController.navigate(Routes.HomeScreen)}
            ) {
                // Circle border back icon
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
                    text = "Assessment",
                    color = primaryTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Box(
                modifier = Modifier
                    .background(secondaryBackgroundColor, RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("1 of 5", fontSize = 12.sp, color = primaryTextColor)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Question Title split across lines
        Text(
            text = "What's your Goal for",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = primaryTextColor
        )
        Text(
            modifier = Modifier.padding(horizontal = 100.dp),
            text = "Today ?",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = accentColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Goal Options
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            options.forEach { (label, icon) ->
                val isSelected = selectedOption == label
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(if (isSelected) accentColor else Color.White)
                            .clickable {
                                selectedOption = label
                                if (label != "Other") {
                                    customGoalText = ""
                                }
                            }
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSelected) selectedIconColor else unselectedIconColor
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = primaryTextColor,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = if (isSelected) Icons.Filled.RadioButtonChecked else Icons.Filled.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = if (isSelected) selectedRadioColor else unselectedRadioColor
                        )
                    }

                    // Custom text input for "Other" option
                    if (label == "Other" && isSelected) {
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = customGoalText,
                            onValueChange = { customGoalText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            placeholder = {
                                Text(
                                    text = "Tell us about your custom goal...",
                                    color = primaryTextColor.copy(alpha = 0.6f),
                                    fontSize = 14.sp
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = primaryTextColor.copy(alpha = 0.3f),
                                focusedTextColor = primaryTextColor,
                                unfocusedTextColor = primaryTextColor,
                                cursorColor = accentColor,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = false,
                            maxLines = 3,
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = accentColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        )
                    }
                }
            }
        }

        // Bigger Continue Button pushed upward
        Button(
            onClick = {navHostController.navigate(Routes.TimeSelectorScreen)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 100.dp), // brings it upward
            colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
            shape = RoundedCornerShape(32.dp)
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}