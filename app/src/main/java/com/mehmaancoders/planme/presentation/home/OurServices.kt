package com.mehmaancoders.planme.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card // Using Material 3 Card
import androidx.compose.material3.CardDefaults // For M3 Card elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme // For ColorScheme and Typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Keep for specific non-theme colors if absolutely needed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource // For string resources
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.R

@Composable
fun ServicesSection(navHostController: NavHostController) {
    Text("Our Services", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    Spacer(Modifier.height(8.dp))
    ServiceItem("Your Calendar", "15 tasks updated", R.drawable.planme_arrow)
    ServiceItem("Your Devices", "Connected for 15 hours", R.drawable.planme_arrow)
    ServiceItem("Overall Progress", "60% out of 100% completed", R.drawable.planme_arrow)
    ServiceItem("Mood Tracker", "SAD → NEUTRAL → HAPPY", R.drawable.planme_arrow)
    ServiceItem("Goal History", "5 tasks accomplished", R.drawable.planme_arrow)
}

@Composable
fun ServiceItem(title: String, subtitle: String, iconId: Int) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(iconId), contentDescription = null)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}
