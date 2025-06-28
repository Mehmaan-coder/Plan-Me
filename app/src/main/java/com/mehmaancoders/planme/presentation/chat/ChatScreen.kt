package com.mehmaancoders.planme.presentation.chat

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes
import androidx.compose.ui.Alignment

data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
fun ChatBotInfoScreen(navHostController: NavHostController) {
    val backgroundColor = Color(0xFFFDF8F6)
    val tagColor = Color(0xFFFFB59B)
    val titleColor = Color(0xFF44322F)
    val subTextColor = Color(0xFF897471)
    val dotActiveColor = Color(0xFF44322F)
    val dotInactiveColor = Color(0xFFD3C4C0)

    var messageInput by remember { mutableStateOf("") }
    var chatMessages by remember { mutableStateOf(listOf<ChatMessage>()) }
    var showBot by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredMessages = if (searchQuery.isNotEmpty()) {
        chatMessages.filter { it.text.contains(searchQuery, ignoreCase = true) }
    } else chatMessages

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 24.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape)
                    .clickable { navHostController.navigate(Routes.HomeScreen) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.planme_back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = "Me Planner",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(end = 16.dp)
                        .clickable {
                            // Toggle simple search (can expand into full screen search)
                            searchQuery = if (searchQuery.isEmpty()) " " else ""
                        }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Settings",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { navHostController.navigate(Routes.SettingsScreen) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (showBot && chatMessages.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.chat_robo),
                contentDescription = "Chatbot Illustration",
                modifier = Modifier
                    .size(260.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(tagColor, shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "LIMITATIONS",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Limited Knowledge",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = titleColor,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "No human being is perfect. So is chatbots.",
                style = MaterialTheme.typography.bodyMedium,
                color = subTextColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(5) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == 2) 10.dp else 6.dp)
                            .clip(CircleShape)
                            .background(if (index == 2) dotActiveColor else dotInactiveColor)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        } else {
            // Chat messages
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                filteredMessages.forEach { message ->
                    val alignment = if (message.isUser) Alignment.End else Alignment.Start
                    val bgColor = if (message.isUser) Color(0xFF9ACF68) else Color(0xFFE7D9D6)
                    val textColor = if (message.isUser) Color.White else Color.Black

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(bgColor, RoundedCornerShape(16.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .widthIn(max = 280.dp)
                        ) {
                            Text(message.text, color = textColor)
                        }
                    }
                }
            }
        }

        // Chat input
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .background(Color.White, shape = RoundedCornerShape(28.dp))
                .border(1.dp, Color(0xFFE7D9D6), shape = RoundedCornerShape(28.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat_dot),
                    contentDescription = null,
                    tint = titleColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = messageInput,
                    onValueChange = { messageInput = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    textStyle = LocalTextStyle.current.copy(color = subTextColor)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = "Send",
                    tint = Color.White,
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFF9ACF68), shape = CircleShape)
                        .clickable {
                            if (messageInput.isNotBlank()) {
                                chatMessages = chatMessages + ChatMessage(messageInput, true)

                                // TODO: Add AI response here
                                chatMessages = chatMessages + ChatMessage("This is a placeholder AI reply.", false)

                                messageInput = ""
                                showBot = false
                            }
                        }
                        .padding(6.dp)
                )
            }
        }
    }
}
