package com.mehmaancoders.planme.presentation.goalselection

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.FlowRow // Already imported
import androidx.compose.foundation.layout.ExperimentalLayoutApi // Already imported

// 🎨 Colors and Styles
val knowledgeScreenBg = Color(0xFFFDF7F3) // Line 21

val knowledgePrimaryText = Color(0xFF3B2B20)
val knowledgeIndicatorBg = Color(0xFFEADFD8) // Line 24
val knowledgeSelectedOptionBg = Color(0xFFA7C879)
val knowledgeUnselectedOptionBg = Color.White
val knowledgeSelectedBorder = Color(0xFFE3EED0)
val knowledgeUnselectedBorder = Color(0xFFE0DAD4)
val knowledgeTagBg = Color(0xFFEADFD8)
val knowledgeTagText = Color(0xFF3B2B20)
val knowledgeButtonBg = Color(0xFF3B2B20)
val knowledgeButtonText = Color.White

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview(showSystemUi = true)
fun KnowledgeSelectionScreen() {
    // ... rest of the code
    val allOptions = listOf("Figma", "Java Scipt", "React", "Fast Api", "Node.js")
    val selectedOptions = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(knowledgeScreenBg)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(32.dp))

            // 🧭 Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Circle, contentDescription = null, tint = knowledgePrimaryText)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Assessment", fontWeight = FontWeight.Bold, color = knowledgePrimaryText)
                }

                Box(
                    modifier = Modifier
                        .background(knowledgeIndicatorBg, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("4 of 4", fontSize = 12.sp, color = knowledgePrimaryText)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Which of these do you\nalready know ?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = knowledgePrimaryText
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 📦 Option Boxes
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                allOptions.forEach { option ->
                    val isSelected = selectedOptions.contains(option)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(if (isSelected) knowledgeSelectedOptionBg else knowledgeUnselectedOptionBg)
                            .border(
                                width = 2.dp,
                                color = if (isSelected) knowledgeSelectedBorder else knowledgeUnselectedBorder,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .clickable {
                                if (isSelected) selectedOptions.remove(option)
                                else selectedOptions.add(option)
                            }
                            .padding(horizontal = 20.dp, vertical = 18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                option,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) Color.White else knowledgePrimaryText,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (isSelected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if (isSelected) Color.White else knowledgePrimaryText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🏷️ Tags
            if (selectedOptions.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Selected:", color = knowledgePrimaryText, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.width(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedOptions.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .background(knowledgeTagBg, RoundedCornerShape(16.dp))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(tag, color = knowledgeTagText, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Remove $tag",
                                        tint = knowledgeTagText,
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clickable { selectedOptions.remove(tag) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // ✅ Continue Button
        Button(
            onClick = { /* handle */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = knowledgeButtonBg)
        ) {
            Text("Continue", color = knowledgeButtonText, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null, tint = knowledgeButtonText)
        }
    }
}
