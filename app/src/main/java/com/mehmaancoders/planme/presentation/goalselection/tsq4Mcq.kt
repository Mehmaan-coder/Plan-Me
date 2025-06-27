package com.mehmaancoders.planme.presentation.goalselection

// import androidx.annotation.DrawableRes // No longer needed for this approach
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // Keep this for Button, ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
// import androidx.compose.ui.res.painterResource // No longer needed if using ImageVector directly
import androidx.compose.ui.graphics.vector.ImageVector // Import ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
// import com.mehmaancoders.planme.R // No longer needed if not using R.drawable for these icons

// Color definitions (Consider moving to your app's theme)
val screenBackgroundPlatform = Color(0xFFFDF7F3)
val primaryTextPlatform = Color(0xFF3B2B20)
val iconTintPlatform = Color(0xFF3B2B20)
val progressIndicatorBackgroundPlatform = Color(0xFFEADFD8)
val platformCardSelectedBg = Color(0xFFA7C879)
val platformCardUnselectedBg = Color.White
val platformCardSelectedContent = Color.White
val platformCardUnselectedContent = Color(0xFF3B2B20)
val platformCardSelectedBorder = Color(0xFFE3EED0)
val platformCardUnselectedBorder = Color(0xFFE0DAD4)
val buttonBackgroundPlatform = Color(0xFF3B2B20)
val buttonContentPlatform = Color.White

// MODIFIED Data class to use ImageVector
data class PlatformOption(val label: String, val iconVector: ImageVector)

@Composable
@Preview(showSystemUi = true)
fun SelectAppPlatformScreen() {
    var selectedPlatform by remember { mutableStateOf("IOS") }

    // This list now correctly matches the modified PlatformOption data class
    val options = listOf(
        PlatformOption("Android", Icons.Default.Android),
        PlatformOption("IOS", Icons.Default.PhoneIphone),
        PlatformOption("WebApp", Icons.Default.Language),
        PlatformOption("Multi-Platform", Icons.Default.AllInclusive)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundPlatform)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null, // Decorative
                        tint = iconTintPlatform
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Assessment",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryTextPlatform
                    )
                }

                Box(
                    modifier = Modifier
                        .background(progressIndicatorBackgroundPlatform, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("1 of 4", fontSize = 12.sp, color = primaryTextPlatform)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Select App platform",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextPlatform
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Options Grid (2x2)
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                options.chunked(2).forEach { rowOptions ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowOptions.forEach { option ->
                            PlatformOptionCard(
                                option = option,
                                isSelected = selectedPlatform == option.label,
                                onClick = { selectedPlatform = option.label },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowOptions.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // Continue Button
        Column(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)) {
            Button(
                onClick = { /* TODO: Handle continue */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundPlatform),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text("Continue", color = buttonContentPlatform, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = buttonContentPlatform
                )
            }
        }
    }
}

@Composable
fun PlatformOptionCard(
    option: PlatformOption,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(24.dp))
            .background(if (isSelected) platformCardSelectedBg else platformCardUnselectedBg)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) platformCardSelectedBorder else platformCardUnselectedBorder,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // MODIFIED Icon to use imageVector
            Icon(
                imageVector = option.iconVector, // Use imageVector from PlatformOption
                contentDescription = "${option.label} platform",
                tint = if (isSelected) platformCardSelectedContent else platformCardUnselectedContent,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = option.label,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) platformCardSelectedContent else platformCardUnselectedContent,
                fontSize = 14.sp
            )
        }
    }
}