package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip // Added this import
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mehmaancoders.planme.R

@Composable
@Preview(showSystemUi = true)
fun GoalOtherInputScreen() {
    var customGoal by remember { mutableStateOf("") }

    // It's good practice to define your colors in your theme
    // e.g., in ui.theme.Color.kt and then use MaterialTheme.colors.surface
    val screenBackgroundColor = Color(0xFFFDF7F3)
    val textColorPrimary = Color(0xFF3B2B20)
    val accentColor = Color(0xFFA7C879)
    val cardBackgroundColor = Color.White
    val textFieldBackgroundColor = Color(0xFFE0E0E0)
    val disabledTextColor = Color.Gray
    val topBarElementColor = Color(0xFF3B2B20)
    val progressIndicatorBackgroundColor = Color(0xFFEADFD8)
    val buttonBackgroundColor = Color(0xFF3B2B20)
    val buttonTextColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundColor)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(modifier = Modifier.height(32.dp))

            // Top Bar
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
                        imageVector = Icons.Default.ArrowBack, // Changed for better UX
                        contentDescription = "Back", // Assuming this is a functional back button
                        tint = topBarElementColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Assessment",
                        color = textColorPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .background(progressIndicatorBackgroundColor, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("1 of 14", fontSize = 12.sp, color = textColorPrimary)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                        append("What’s your Goal for ")
                    }
                    withStyle(
                        SpanStyle(
                            color = accentColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    ) {
                        append("Today")
                    }
                    append(" ?")
                },
                color = textColorPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // "Other" Option (Active)
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp)) // This modifier needs the import
                        .background(cardBackgroundColor)
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Checkroom, // T-shirt style icon
                        contentDescription = null, // Decorative
                        tint = textColorPrimary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Other",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textColorPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null, // Decorative, indicates selection
                        tint = textColorPrimary
                    )
                }

                // Text Input Field
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        // Consider applying clip here as well if you want rounded corners
                        // .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(
                            textFieldBackgroundColor,
                            RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                        ) // Rounded bottom corners
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    TextField(
                        value = customGoal,
                        onValueChange = { customGoal = it },
                        placeholder = {
                            Text(
                                text = "Write your Goal Here....",
                                color = disabledTextColor,
                                fontSize = 16.sp
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent, // Make TextField transparent
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = textColorPrimary // Set cursor color
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // Continue Button
        Column(modifier = Modifier.padding(bottom = 24.dp)) { // Added Column for spacing
            Button(
                onClick = { /* TODO: Handle next action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(text = "Continue", color = buttonTextColor, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null, // Decorative
                    tint = buttonTextColor
                )
            }
        }
    }
}