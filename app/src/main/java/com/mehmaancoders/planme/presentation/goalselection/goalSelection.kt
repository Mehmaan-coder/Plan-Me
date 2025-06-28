package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

@Composable
fun GoalSelectionScreen(
    navHostController: NavHostController,
    viewModel: GoalSelectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val options = listOf(
        "Watch Imdb Top 100" to Icons.Default.FavoriteBorder,
        "Create an App" to Icons.Default.Android,
        "Learn how to do a Cartwheel" to Icons.Default.Flag,
        "Quit Smoking" to Icons.Default.SentimentSatisfied,
        "Other" to Icons.Default.MoreHoriz
    )

    val backgroundColor = Color(0xFFFDF7F3)
    val primaryTextColor = Color(0xFF3B2B20)
    val accentColor = Color(0xFFA7C879)
    val secondaryBackgroundColor = Color(0xFFEADFD8)
    val buttonBackgroundColor = Color(0xFF3B2B20)
    val selectedIconColor = Color.White
    val unselectedIconColor = Color.Gray
    val selectedRadioColor = Color.White
    val unselectedRadioColor = Color(0xFF3B2B20)

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { navHostController.navigate(Routes.HomeScreen) }
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = primaryTextColor, shape = CircleShape)
                        .clickable { navHostController.navigate(Routes.HomeScreen) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.planme_back),
                        contentDescription = "Back",
                        tint = primaryTextColor,
                        modifier = Modifier.size(24.dp)
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

        Text("What's your Goal for", fontWeight = FontWeight.Bold, fontSize = 36.sp, color = primaryTextColor)
        Text(modifier = Modifier.padding(horizontal = 100.dp), text = "Today ?", fontWeight = FontWeight.Bold, fontSize = 36.sp, color = accentColor)

        Spacer(modifier = Modifier.height(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.weight(1f)) {
            options.forEach { (label, icon) ->
                val isSelected = uiState.selectedOption == label

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(if (isSelected) accentColor else Color.White)
                            .clickable {
                                viewModel.updateSelectedOption(label)
                                if (label != "Other") {
                                    viewModel.updateCustomGoalText("")
                                }
                            }
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, contentDescription = null, tint = if (isSelected) selectedIconColor else unselectedIconColor)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = primaryTextColor, modifier = Modifier.weight(1f))
                        Icon(if (isSelected) Icons.Filled.RadioButtonChecked else Icons.Filled.RadioButtonUnchecked, contentDescription = null, tint = if (isSelected) selectedRadioColor else unselectedRadioColor)
                    }

                    if (label == "Other" && isSelected) {
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = uiState.customGoalText,
                            onValueChange = { viewModel.updateCustomGoalText(it) },
                            placeholder = { Text("Enter your custom goal...", color = Color.Gray, fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                                focusedTextColor = primaryTextColor,
                                unfocusedTextColor = primaryTextColor,
                                cursorColor = accentColor
                            ),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = false,
                            maxLines = 3,
                            enabled = !uiState.isLoading
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                viewModel.saveGoal(
                    onSuccess = { navHostController.navigate(Routes.TimeSelectorScreen) },
                    onError = {
                        errorMessage = it
                        showErrorDialog = true
                    }
                )
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
            shape = RoundedCornerShape(32.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Saving...", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            } else {
                Text("Continue", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
            }
        }
    }
}