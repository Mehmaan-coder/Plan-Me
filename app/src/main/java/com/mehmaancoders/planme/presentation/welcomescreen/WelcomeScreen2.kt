package com.mehmaancoders.planme.presentation.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

@Composable
fun WelcomeScreen2(navHostController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(painter = painterResource(id = R.drawable.planme_welcome2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(500.dp))

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF9C6F51))
            )
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFD3D3D3))
            )
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFD3D3D3))
            )
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFD3D3D3))
            )
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFD3D3D3))
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(text = "Your Goals,", fontSize = 35.sp, fontWeight = FontWeight.Medium, color = colorResource(id = R.color.brown))

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "Smarter", fontSize = 35.sp, fontWeight = FontWeight.Medium, color = colorResource(id = R.color.planmegreen))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {navHostController.navigate(Routes.WelcomeScreen3)},
            modifier = Modifier.size(80.dp).clip(CircleShape), colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.brown),
                contentColor = Color.White
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.planme_arrow), contentDescription = null, modifier = Modifier.size(100.dp)
            )
        }
    }
}