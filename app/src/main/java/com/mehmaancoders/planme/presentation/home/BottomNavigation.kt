package com.mehmaancoders.planme.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics // Or Outlined or Rounded version
import androidx.compose.material.icons.filled.Chat // Or Outlined or Rounded
import androidx.compose.material.icons.filled.Home // Or Outlined or Rounded
import androidx.compose.material.icons.filled.Person // Or Outlined or Rounded
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf // More efficient for simple index state
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier // If you need to pass modifiers
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mehmaancoders.planme.R

@Composable
@Preview(showSystemUi = true)
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = true,
            onClick = { /* TODO */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Chat, contentDescription = null) },
            label = { Text("AI") },
            selected = false,
            onClick = { /* TODO */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Analytics, contentDescription = null) },
            label = { Text("Stats") },
            selected = false,
            onClick = { /* TODO */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = false,
            onClick = { /* TODO */ }
        )
    }
}
