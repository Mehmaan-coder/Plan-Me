package com.mehmaancoders.planme.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite // Assuming this is the intended icon
// If you want a different Favorite icon (e.g., outlined), use Icons.Outlined.Favorite
import androidx.compose.material3.Card // Material 3 Card
import androidx.compose.material3.CardDefaults // Material 3 CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme // For ColorScheme and Typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton // For "See All"
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// import androidx.compose.ui.graphics.Color // Only if you have a very specific non-theme color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mehmaancoders.planme.R

@Composable
@Preview(showSystemUi = true)
fun MindfulArticlesSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Mindful Articles", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Text("See All", fontSize = 12.sp)
    }

    Spacer(Modifier.height(8.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        ArticleCard()
        ArticleCard()
    }
}

@Composable
fun ArticleCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(180.dp)
            .height(140.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("MENTAL HEALTH", fontSize = 10.sp)
            Spacer(Modifier.height(4.dp))
            Text("Will meditation help you get out from the rat race?", fontSize = 12.sp, maxLines = 2)
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(16.dp))
                Text("987", fontSize = 10.sp)
                Spacer(Modifier.width(8.dp))
                Text("5K views", fontSize = 10.sp)
            }
        }
    }
}
