package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GoalSelectionScreen() {
    var selectedOption by remember { mutableStateOf("Create an App") }

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
            .padding(horizontal = 24.dp)
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
                modifier = Modifier.clickable { /* Handle back */ }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = primaryTextColor
                )
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
                Text("1 of 14", fontSize = 12.sp, color = primaryTextColor)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Question Title split across lines
        Text(
//            modifier = Modifier.padding(32.dp),
            text = "What’s your Goal for",
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(if (isSelected) accentColor else Color.White)
                        .clickable { selectedOption = label }
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
            }
        }

        // Bigger Continue Button pushed upward
        Button(
            onClick = { /* Handle continue action */ },
            modifier = Modifier
                .fillMaxWidth()
//                .height(100.dp)
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

@Preview(showSystemUi = true)
@Composable
fun PreviewGoalSelectionScreen() {
    GoalSelectionScreen()
}
