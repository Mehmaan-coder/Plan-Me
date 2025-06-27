package com.mehmaancoders.planme.presentation.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

@Composable
fun WelcomeScreen(navHostController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.planme_bglogo),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = "Welcome to the ultimate",
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 10.dp),
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.brown)
        )

        Row {

            Text(
                text = "Productivity",
                color = colorResource(id = R.color.brown60),
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "experience",
                color = colorResource(id = R.color.brown),
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            text = "Your mindful AI life planner",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 10.dp),
            color = colorResource(id = R.color.greyplanme)
        )

        Image(
            modifier = Modifier
                .padding(top = 30.dp)
                .size(350.dp),
            painter = painterResource(id = R.drawable.planme_welcome),
            contentDescription = null
        )

        Button(
            onClick = {navHostController.navigate(Routes.WelcomeScreen2)},
            modifier = Modifier
                .size(200.dp, 80.dp)
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.brown),
                contentColor = Color.White
            )
        ) {
            Row {
                Text(text = "Get Started", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.planme_arrow),
                    contentDescription = null
                )
            }
        }
        Row(modifier = Modifier.padding(top = 10.dp)) {
            Text(
                text = "Already have an account?",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.greyplanme)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Sign In",
                modifier = Modifier.clickable{
                    navHostController.navigate(Routes.SignInScreen)
                },
                fontSize = 15.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.orange40)
            )
        }
    }
}