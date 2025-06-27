package com.mehmaancoders.planme.presentation.goalselection
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.mehmaancoders.planme.R
@Composable
@Preview(showSystemUi = true)
fun FetchingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA1BE5C)) // Base green background
    ) {
        // Large background circles
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            drawCircle(
                color = Color(0xFF8AA34D),
                radius = width * 0.8f,
                center = Offset(x = width * 0.2f, y = height * 0.25f)
            )
            drawCircle(
                color = Color(0xFF8AA34D),
                radius = width * 0.4f,
                center = Offset(x = width * 0.9f, y = height * 0.1f)
            )
            drawCircle(
                color = Color(0xFF8AA34D),
                radius = width * 0.5f,
                center = Offset(x = width * 0.8f, y = height * 0.55f)
            )
            drawCircle(
                color = Color(0xFF8AA34D),
                radius = width * 0.6f,
                center = Offset(x = width * 0.4f, y = height * 0.85f)
            )
        }

        // Centered content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fetching Data...",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Mouse,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Shake screen to interact!",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
