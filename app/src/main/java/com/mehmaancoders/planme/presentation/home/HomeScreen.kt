package com.mehmaancoders.planme.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mehmaancoders.planme.presentation.navigation.Routes
import com.mehmaancoders.planme.utils.BottomNavigationBar

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    val username = user?.displayName ?: "User"
    val profileImageUrl = user?.photoUrl?.toString() ?: ""


    Scaffold(
        containerColor = Color(0xFFF9F6F4),
        bottomBar = {
            BottomNavigationBar(navHostController = navHostController)
        }
    ) { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color(0xFFF9F6F4))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        TopBarSection(username = username, profileImageUrl = profileImageUrl, navHostController = navHostController)
        Spacer(Modifier.height(12.dp))
        GoalOverviewSection(navHostController)
        Spacer(Modifier.height(16.dp))
        ServicesSection(navHostController)
        Spacer(Modifier.height(16.dp))
        TalkToAISection(navHostController)
        Spacer(Modifier.height(16.dp))
        MindfulArticlesSection(navHostController)
        Spacer(Modifier.height(60.dp)) // For bottom nav
    }
}
    }
