package com.mehmaancoders.planme.presentation.insights

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

@Composable
fun InsightsScreen(navHostController: NavHostController) {
    val background = Color(0xFFFDF8F6)
    val darkBrown = Color(0xFF44322F)
    val lightGray = Color(0xFF897471)
    val graphLineColor = Color(0xFF44322F)

    var selectedTimeframe by remember { mutableStateOf("All") }

    val timeframes = listOf("All", "Days", "Weeks", "Months", "Years")
    val moodLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val moodIcons = listOf(
        R.drawable.ic_mood_happy,
        R.drawable.ic_mood_calm,
        R.drawable.ic_mood_sad,
        R.drawable.ic_mood_depressed,
        R.drawable.ic_mood_depressed,
        R.drawable.ic_mood_depressed,
        R.drawable.ic_mood_sad
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        // Top bar
        Row(modifier = Modifier.fillMaxWidth().clickable{navHostController.navigate(Routes.HomeScreen)}, horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(
                painter = painterResource(id = R.drawable.planme_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.dp, darkBrown, CircleShape)
                    .padding(8.dp),
                tint = darkBrown
            )


        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Insights",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = darkBrown
        )
        Text(
            "See your progress throughout the day.",
            style = MaterialTheme.typography.bodyMedium,
            color = lightGray
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_stats),
            contentDescription = "Stats",
            modifier = Modifier
                .size(36.dp)
                .background(darkBrown, shape = CircleShape)
                .padding(8.dp),
            tint = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Timeframe tabs
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            timeframes.forEach { label ->
                val selected = label == selectedTimeframe
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (selected) darkBrown else Color.Transparent)
                        .border(1.dp, darkBrown, RoundedCornerShape(20.dp))
                        .clickable { selectedTimeframe = label }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(label, color = if (selected) Color.White else darkBrown, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Curved Mood Chart (Smooth Path)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.Transparent)
                .border(
                    width = 1.dp,
                    color = Color(0xFFE0CFC7),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Canvas(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 24.dp)) {
                val points = listOf(0.5f, 0.8f, 0.4f, 0.2f, 0.7f, 0.5f, 0.3f)
                val widthStep = size.width / (points.size - 1)
                val path = Path()

                for (i in 0 until points.size) {
                    val x = i * widthStep
                    val y = size.height * (1 - points[i])

                    if (i == 0) {
                        path.moveTo(x, y)
                    } else {
                        val prevX = (i - 1) * widthStep
                        val prevY = size.height * (1 - points[i - 1])
                        val controlX = (prevX + x) / 2
                        path.cubicTo(controlX, prevY, controlX, y, x, y)
                    }
                }

                drawPath(
                    path = path,
                    color = graphLineColor,
                    style = Stroke(width = 4f, cap = StrokeCap.Round)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mood prediction row
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .border(1.dp, Color(0xFFE0CFC7), RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("AI Mood Predictions", color = darkBrown, fontWeight = FontWeight.Medium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Next 1w", color = lightGray, fontSize = 12.sp)
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = lightGray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                moodIcons.zip(moodLabels).forEach { (icon, label) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = label,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(label, color = lightGray, fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}
