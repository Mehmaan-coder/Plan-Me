package com.mehmaancoders.planme.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme // For accessing M3 ColorScheme and Typography
import androidx.compose.material3.Text // Using Material 3 Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource // For string resources
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.R

@Composable
fun GoalOverviewSection(navHostController: NavHostController) {
    Text("Goal Overview", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    Spacer(Modifier.height(8.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        GoalCard("Create an App", "80\nHealthy", Color(0xFFB5DB9C))
        GoalCard("Reduce Screen Time", "High", Color(0xFFFFA361))
    }
}

@Composable
fun GoalCard(title: String, value: String, color: Color) {
    Column(
        modifier = Modifier
            .background(color, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(Modifier.height(8.dp))
        Text(value, fontSize = 18.sp)
    }
}
