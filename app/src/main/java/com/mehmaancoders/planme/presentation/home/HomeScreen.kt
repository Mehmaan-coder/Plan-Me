package com.mehmaancoders.planme.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    username: String,
    profileImageUrl: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F6F4))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        TopBarSection(username = username, profileImageUrl = profileImageUrl)
        Spacer(Modifier.height(12.dp))
        GoalOverviewSection()
        Spacer(Modifier.height(16.dp))
        ServicesSection()
        Spacer(Modifier.height(16.dp))
        TalkToAISection()
        Spacer(Modifier.height(16.dp))
        MindfulArticlesSection()
        Spacer(Modifier.height(60.dp)) // For bottom nav
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        username = "Manav",
        profileImageUrl = "https://example.com/profile.jpg"
    )
}
