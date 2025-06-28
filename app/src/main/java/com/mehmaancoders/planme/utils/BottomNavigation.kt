package com.mehmaancoders.planme.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

@Composable
fun BottomNavigationBar(
    navHostController: NavHostController,
    onFabClick: () -> Unit = {},
    onItemSelected: (Int) -> Unit = {},
    selectedItem: Int = 0
) {
    val fabSize = 72.dp
    val fabRadius = fabSize / 2

    Box {
        // Background with cutout
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            color = Color.White,
            shadowElevation = 10.dp,
            shape = bottomBarWithCutoutShape(fabRadius)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(
                    iconRes = R.drawable.explore,
                    selected = selectedItem == 0,
                    onClick = { onItemSelected(0) }
                )
                BottomNavItem(
                    iconRes = R.drawable.chats,
                    selected = selectedItem == 1,
                    onClick = { onItemSelected(1) }
                )
                Spacer(modifier = Modifier.width(fabSize)) // FAB space
                BottomNavItem(
                    iconRes = R.drawable.analytics,
                    selected = selectedItem == 2,
                    onClick = { onItemSelected(2) }
                )
                BottomNavItem(
                    iconRes = R.drawable.profileicon,
                    selected = selectedItem == 3,
                    onClick = { onItemSelected(3) }
                )
            }
        }

        // Floating Action Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            FloatingActionButton(
                onClick = {navHostController.navigate(Routes.GoalSelectionScreen)},
                containerColor = Color(0xFFA5C87A),
                contentColor = Color.White,
                modifier = Modifier
                    .size(fabSize)
                    .offset(y = (-28).dp),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
fun BottomNavItem(
    @DrawableRes iconRes: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val iconColor = if (selected) Color(0xFF4C2B1C) else Color(0xFFBFAFA6)
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(if (selected) Color(0xFFF2EDEB) else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            alignment = Alignment.Center
        )
    }
}

// Custom shape with cutout
@Composable
fun bottomBarWithCutoutShape(fabRadius: Dp): Shape {
    val fabRadiusPx = with(LocalDensity.current) { fabRadius.toPx() }
    val cutoutWidth = fabRadiusPx * 2

    return GenericShape { size, _ ->
        val width = size.width
        val height = size.height
        val centerX = width / 2

        moveTo(0f, 0f)
        lineTo(centerX - cutoutWidth / 2, 0f)

        cubicTo(
            centerX - fabRadiusPx, 0f,
            centerX - fabRadiusPx, fabRadiusPx,
            centerX, fabRadiusPx + 10f
        )

        cubicTo(
            centerX + fabRadiusPx, fabRadiusPx,
            centerX + fabRadiusPx, 0f,
            centerX + cutoutWidth / 2, 0f
        )

        lineTo(width, 0f)
        lineTo(width, height)
        lineTo(0f, height)
        close()
    }
}
