package com.mehmaancoders.planme.presentation.assesment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mehmaancoders.planme.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview(showSystemUi = true)
fun AssessmentScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(
                        width = 5.dp,
                        color = colorResource(id = R.color.brown),
                        shape = RoundedCornerShape(12.dp),
                    )
                ,
                contentAlignment = Alignment.Center
            ) {
                Text("(", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5B3C29))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Assessment",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF5B3C29)
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "11 of 14",
                fontSize = 15.sp,
                modifier = Modifier
                    .background(Color(0xFFEDE0DB), RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                color = Color(0xFF5B3C29)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column {
            Text(
                text = "What are you using this",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4C2B1C),
                lineHeight = 32.sp,
            )
            Text(
                text = "app for?",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4C2B1C),
                lineHeight = 32.sp,
            )
        }

        Image(
            painter = painterResource(id = R.drawable.planme_assessment),
            contentDescription = null,
            modifier = Modifier
                .size(350.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .border(1.dp, Color(0xFF6DB87C), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            FlowRow {
                Tag("Learn New Skills", Color(0xFF6DB87C))
                Tag("Fitness", Color(0xFF6DB87C))
                Tag("Sleep Management", Color(0xFF6DB87C))
                Tag("Business Launch prep...", Color.LightGray, false)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_edit),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "2/10", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Most Common Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Most Common: ", fontSize = 14.sp, color = Color(0xFF4C2B1C))
            CommonTag("Build Home")
            Spacer(modifier = Modifier.width(8.dp))
            CommonTag("Vacation Planning")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        Button(
            onClick = { /* TODO */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C2B1C)),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Continue", color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("→", color = Color.White, fontSize = 20.sp)
        }
    }
}

@Composable
fun Tag(text: String, bgColor: Color, isActive: Boolean = true) {
    Text(
        text = text,
        fontSize = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(end = 8.dp, bottom = 8.dp)
            .background(bgColor.copy(alpha = 0.2f), RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        color = if (isActive) bgColor else Color.Gray
    )
}

@Composable
fun CommonTag(text: String) {
    Row(
        modifier = Modifier
            .background(Color(0xFFFF8B42), RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, color = Color.White, fontSize = 12.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Text("×", color = Color.White)
    }
}
