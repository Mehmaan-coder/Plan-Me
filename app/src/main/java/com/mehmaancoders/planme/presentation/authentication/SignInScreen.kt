@file:JvmName("SignInScreenKt")

package com.mehmaancoders.planme.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.mehmaancoders.planme.R
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavHostController
import com.mehmaancoders.planme.presentation.navigation.Routes

@Composable
fun SignInScreen(navHostController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF7F4))
    ) {
        Box(
            modifier = Modifier
                .width(1000.dp)
                .height(200.dp)
                .background(
                    color = Color(0xFF91B572),
                    shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.planme_white),
                contentDescription = "App Logo",
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Sign In To Plan-Me",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = Color(0xFF4C2B1C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text("Email Address", color = Color(0xFF4C2B1C), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter your Email...", fontWeight = FontWeight.Bold, color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        color = Color(0xFFB4D48B),
                        shape = RoundedCornerShape(50)
                    ),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.run {
                    colors(
                        focusedBorderColor = Color(0xFFB4D48B),
                        unfocusedBorderColor = Color(0xFFB4D48B),
                        focusedTextColor = Color(0xFF4C2B1C),
                        unfocusedTextColor = Color(0xFF4C2B1C),
                        cursorColor = Color(0xFF4C2B1C),
                        disabledTextColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Password", color = Color(0xFF4C2B1C), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Enter your password...", fontWeight = FontWeight.Bold, color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(50)),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color(0xFF4C2B1C),
                    unfocusedTextColor = Color(0xFF4C2B1C),
                    cursorColor = Color(0xFF4C2B1C),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Password Visibility",
                            tint = Color.Gray
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {navHostController.navigate(Routes.ConnectDevicesScreen)},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C2B1C))
            ) {
                Text("Sign In", color = Color.White, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Image(painterResource(id = R.drawable.planme_arrow), contentDescription = null, modifier = Modifier.size(24.dp))
            }

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SocialButton(icon = R.drawable.facebook)
                SocialButton(icon = R.drawable.google)
                SocialButton(icon = R.drawable.instagram)
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Skip For Now",
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = Color(0xFFFF9021),
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally).clickable{navHostController.navigate(Routes.ConnectDevicesScreen)}
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Don’t have an account? ", color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text("Sign Up", color = Color(0xFFFF9021), fontSize = 18.sp, fontWeight = FontWeight.SemiBold, textDecoration = TextDecoration.Underline, modifier = Modifier.clickable{
                    navHostController.navigate(Routes.SignUpScreen)
                })
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Forgot Password",
                color = Color(0xFFFF9021),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun SocialButton(icon: Int) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Color.Transparent, shape = CircleShape)
            .border(1.dp, Color.LightGray, shape = CircleShape)
            .clickable { /* Handle social login */ },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
