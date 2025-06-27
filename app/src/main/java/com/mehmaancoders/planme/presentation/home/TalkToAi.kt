package com.mehmaancoders.planme.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card // Material 3 Card
import androidx.compose.material3.CardDefaults // Material 3 CardDefaults
import androidx.compose.material3.FloatingActionButton // Material 3 FAB
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme // For ColorScheme and Typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
// import androidx.compose.ui.graphics.Color // Only if you have a very specific non-theme color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.R

@Composable
fun TalkToAISection(navHostController: NavHostController) {
    Text("Talk to the AI", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    Spacer(Modifier.height(8.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("2541 Conversations", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("83 left this month", fontSize = 12.sp)
            Text("Go Pro. Now!", fontSize = 12.sp, color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }

    Spacer(Modifier.height(16.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        FloatingActionButton(onClick = { /* TODO: Create Task */ }, containerColor = MaterialTheme.colorScheme.primaryContainer) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
        FloatingActionButton(onClick = { /* TODO: Settings */ }, containerColor = MaterialTheme.colorScheme.onPrimaryContainer) {
            Icon(Icons.Default.Settings, contentDescription = null)
        }
    }
}
