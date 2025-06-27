package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // Keep for Material 2 components if used
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
// Assuming you might be transitioning or want Material 3 components for consistency:
import androidx.compose.material3.Button // Material 3 Button
import androidx.compose.material3.ButtonDefaults // Material 3 ButtonDefaults
import androidx.compose.material3.Icon // Material 3 Icon
import androidx.compose.material3.Text // Material 3 Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

// Consider moving these colors to your app's theme (ui.theme/Color.kt)
val screenBackgroundTf = Color(0xFFFDF7F3)
val primaryTextTf = Color(0xFF3B2B20)
val iconTintTf = Color(0xFF3B2B20)
val progressIndicatorBackgroundTf = Color(0xFFEADFD8)

val optionCardSelectedBgPersonal = Color(0xFFA7C879) // Example: Green for personal
val optionCardSelectedBgCommercial = Color(0xFF4A90E2) // Example: Blue for commercial
val optionCardUnselectedBgTf = Color.White
val optionCardSelectedContentTf = Color.White
val optionCardUnselectedContentTf = Color(0xFF3B2B20) // Same as primaryTextTf
val optionCardIconBg = Color(0xFFF3F1EF)

val optionCardSelectedBorderPersonal = Color(0xFFE3EED0)
val optionCardUnselectedBorderTf = Color(0xFFE0DAD4)
val optionCardSelectedBorderCommercial = Color(0xFFC2D9F3) // Example border for commercial

val buttonBackgroundTf = Color(0xFF3B2B20)
val buttonContentTf = Color.White

// Enum to represent the project type for better type safety
enum class ProjectType {
    PERSONAL, COMMERCIAL, NONE // NONE for initial unselected state if needed
}

@Composable
@Preview(showSystemUi = true)
fun PainAssessmentQuestionScreen() {
    var selectedOption by remember { mutableStateOf("No Physical Pain At All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF7F3))
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(32.dp))

            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Circle, contentDescription = null, tint = Color(0xFF3B2B20))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Assessment", fontWeight = FontWeight.Bold, color = Color(0xFF3B2B20))
                }
                Box(
                    modifier = Modifier
                        .background(Color(0xFFEADFD8), RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("2 of 4", fontSize = 12.sp, color = Color(0xFF3B2B20))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Is this a personal project\nor a commercial one?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3B2B20)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Option 1 - Unselected
            PainOptionCard(
                title = "Yes, one or multiple",
                subtitle = "I’m experiencing physical pain in\ndifferent place over my body.",
                selected = selectedOption == "Yes",
                onClick = { selectedOption = "Yes" },
                icon = Icons.Default.Check,
                selectedBackground = Color.White,
                selectedTextColor = Color(0xFF3B2B20),
                radioTint = Color(0xFF3B2B20)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Option 2 - Selected
            PainOptionCard(
                title = "No Physical Pain At All",
                subtitle = "I’m not experiencing any physical pain\nin my body at all :)",
                selected = selectedOption == "No Physical Pain At All",
                onClick = { selectedOption = "No Physical Pain At All" },
                icon = Icons.Default.Close,
                selectedBackground = Color(0xFFA7C879),
                selectedTextColor = Color.White,
                radioTint = Color.White
            )
        }

        // Continue Button
        Button(
            onClick = { /* Handle next */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B2B20))
        ) {
            Text("Continue", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
        }
    }
}

@Composable
fun PainOptionCard(
    title: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    selectedBackground: Color,
    selectedTextColor: Color,
    radioTint: Color
) {
    val borderColor = if (selected) Color(0xFFE3EED0) else Color(0xFFE0DAD4)
    val iconCircleBg = if (selected) Color.White.copy(alpha = 0.2f) else Color(0xFFF3F1EF)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(selectedBackground)
            .border(2.dp, borderColor, RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconCircleBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = selectedTextColor)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, color = selectedTextColor)
                Text(
                    subtitle,
                    fontSize = 14.sp,
                    color = if (selected) selectedTextColor.copy(alpha = 0.9f) else Color.Gray
                )
            }

            Icon(
                imageVector = if (selected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = radioTint
            )
        }
    }
}
