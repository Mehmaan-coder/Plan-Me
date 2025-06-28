package com.mehmaancoders.planme.presentation.iot

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mehmaancoders.planme.R

@Preview(showSystemUi = true)
@Composable
fun DevicePanelScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF7F3))
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Top bar with title + gear
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Devices, contentDescription = null, tint = Color(0xFF3B2B20))
            Text(
                text = "Your Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = Color(0xFF3B2B20)
            )
            IconButton(onClick = { /* Handle settings */ }) {
                Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White, modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFF3B2B20), shape = CircleShape)
                    .padding(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your Devices",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = Color(0xFF3B2B20)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Journals Header + Sort
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("All Journals", fontWeight = FontWeight.Bold, color = Color(0xFF3B2B20))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFF3B2B20), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Icon(Icons.Default.Sort, contentDescription = null, tint = Color(0xFF3B2B20))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Newest", color = Color(0xFF3B2B20), fontSize = 14.sp)
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFF3B2B20))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Smart Journals
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SmartDeviceCard(
                iconBg = Color(0xFFA7C879),
//                modifier = Modifier.size(200.dp),
                icon = R.drawable.ic_smile,
                label = "DESK",
                labelColor = Color(0xFFA7C879),
                title = "Homey",
                subtitle = "Current Profile : Work"
            )

            SmartDeviceCard(
                iconBg = Color(0xFFFF6B00),
                icon = R.drawable.ic_night,
                label = "BEDROOM",
                labelColor = Color(0xFFFFCFC0),
                title = "8Sleep",
                subtitle = "Status : Inactive"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Summary Header
        Text("Device Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF3B2B20))

        Spacer(modifier = Modifier.height(16.dp))

        // Summary boxes
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DeviceSummaryBox(
                iconBg = Color(0xFFDDEACC),
                icon = R.drawable.ic_doc,
                title = "6/8",
                subtitle = "Connected"
            )
            DeviceSummaryBox(
                iconBg = Color(0xFFFBEDE7),
                icon = R.drawable.ic_inactive,
                title = "Inactive",
                subtitle = "5"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SmartDeviceCard(
    iconBg: Color,
    icon: Int,
    label: String,
    labelColor: Color,
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(id = icon), contentDescription = null, tint = Color.White)
        }

        Spacer(modifier = Modifier.height(72.dp).width(180.dp))

        Text(
            text = label,
            color = labelColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(labelColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF3B2B20), fontSize = 16.sp)
        Text(subtitle, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun DeviceSummaryBox(iconBg: Color, icon: Int, title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(id = icon), modifier = Modifier.size(36.dp), contentDescription = null, tint = Color(0xFF3B2B20))
        }

        Spacer(modifier = Modifier.width(128.dp))

        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF3B2B20))
        Text(subtitle, color = Color.Gray, fontSize = 12.sp)
    }
}
