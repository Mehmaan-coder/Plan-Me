package com.mehmaancoders.planme.presentation.iot

import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.*
import androidx.compose.material.* // Keep for Scaffold if it's the M2 Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CardDefaults // For M3 Card elevation/colors if needed
import androidx.compose.material3.ElevatedCard // If you prefer M3 elevated card
import androidx.compose.material3.FloatingActionButton // M3 FAB
import androidx.compose.material3.Icon // M3 Icon
import androidx.compose.material3.Scaffold // M3 Scaffold
import androidx.compose.material3.Text // M3 Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mehmaancoders.planme.R

@Composable
@Preview(showSystemUi = true)
fun SmartDeviceProfileScreen() {
    val recentActions = listOf(
        Triple("Turn on Atomizer", "1 Device • Homey", R.drawable.heartbeat),
        Triple("Switch Playlist : Work", "45 Mins • Spotify", R.drawable.headphone),
        Triple("Office Lights : Bright", "3 Devices • Homey | Philips Hue", R.drawable.officelights)
    )

    val pastActions = listOf(
        Triple("More Xans this Xmas...", "478 Total • Sad", R.drawable.store)
    )

    // Using Material 3 Scaffold
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add new profile */ },
                containerColor = Color(0xFFA7C879) // Changed from backgroundColor
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        },
        // bottomBar = { // Ignoring bottom navigation as requested
        // BottomNavigation(backgroundColor = Color.White) {
        // BottomNavigationItem(icon = { Icon(Icons.Default.Home, null) }, selected = true, onClick = {})
        // BottomNavigationItem(icon = { Icon(Icons.Default.ChatBubbleOutline, null) }, selected = false, onClick = {})
        // Spacer(modifier = Modifier.weight(1f)) // center FAB space
        // BottomNavigationItem(icon = { Icon(Icons.Default.BarChart, null) }, selected = false, onClick = {})
        // BottomNavigationItem(icon = { Icon(Icons.Default.Person, null) }, selected = false, onClick = {})
        // }
        // }
    ) { paddingValues -> // Renamed 'it' to 'paddingValues' for clarity
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
                .background(Color(0xFFFDF7F3))
        ) {
            // Top Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF6B00), shape = RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "Profile : Work-Day",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Devices, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("1571 Total", color = Color.White)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ChatBubble, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("32 Left this Month", color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recent Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically // Added for better alignment
            ) {
                Text("Recent (4)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF3B2B20))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, Color(0xFF3B2B20), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Sort, contentDescription = null, tint = Color(0xFF3B2B20))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Newest", color = Color(0xFF3B2B20), fontSize = 14.sp) // Added font size for consistency
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFF3B2B20))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Recent Items
            // Use weight for LazyColumn if it's meant to scroll within remaining space
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp), // Added vertical padding
                verticalArrangement = Arrangement.spacedBy(12.dp) // Spacing between items
                // modifier = Modifier.weight(1f) // Add this if there's other content below that should not be pushed off-screen
            ) {
                items(recentActions) { (title, subtitle, iconRes) ->
                    DeviceActionCard(title, subtitle, iconRes)
                    // Spacer for between items is handled by LazyColumn's verticalArrangement
                }

                // Past Header
                item {
                    Spacer(modifier = Modifier.height(16.dp)) // Increased spacing before "Past"
                    Text("Past (16)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF3B2B20))
                    // Spacer(modifier = Modifier.height(12.dp)) // This spacer can be removed if verticalArrangement is used below
                }

                // Past Items
                items(pastActions) { (title, subtitle, iconRes) ->
                    DeviceActionCard(title, subtitle, iconRes)
                }
            }
        }
    }
}

@Composable
fun DeviceActionCard(title: String, subtitle: String, iconRes: Int) {
    // Using Material 3 ElevatedCard for a more Material You feel, or stick with M2 Card if preferred
    ElevatedCard( // Or androidx.compose.material.Card
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // M3 elevation
        // For M2 Card: elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // fillMaxWidth here for the Row inside the Card
                .padding(16.dp)
            // .background(Color.White) // Card container color handles this in M3
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE3E8E1)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = title, // Added content description
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF3B2B20))
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            }

            Icon(Icons.Default.MoreVert, contentDescription = "More options for $title", tint = Color.Gray) // Added content description
        }
    }
}