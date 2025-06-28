package com.mehmaancoders.planme.presentation.authentication

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

@Composable
fun SignInScreen(navHostController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // Google SignIn setup - Updated to show account picker
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            isLoading = true
            auth.signInWithCredential(credential)
                .addOnCompleteListener { signInTask ->
                    isLoading = false
                    if (signInTask.isSuccessful) {
                        navHostController.navigate(Routes.ConnectDevicesScreen) {
                            popUpTo(Routes.SignInScreen) { inclusive = true }
                        }
                    } else {
                        errorMessage = "Google Sign-In failed: ${signInTask.exception?.message}"
                    }
                }
        } catch (e: Exception) {
            isLoading = false
            errorMessage = "Google Sign-In error: ${e.localizedMessage}"
        }
    }

    // Function to validate input fields
    fun validateInputs(): Boolean {
        when {
            email.trim().isEmpty() -> {
                errorMessage = "Please enter your email address"
                return false
            }
            password.trim().isEmpty() -> {
                errorMessage = "Please enter your password"
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                errorMessage = "Please enter a valid email address"
                return false
            }
            else -> {
                errorMessage = null
                return true
            }
        }
    }

    // Function to handle email/password sign in
    fun handleEmailSignIn() {
        if (!validateInputs()) return

        isLoading = true
        errorMessage = null

        auth.signInWithEmailAndPassword(email.trim(), password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    navHostController.navigate(Routes.ConnectDevicesScreen) {
                        popUpTo(Routes.SignInScreen) { inclusive = true }
                    }
                } else {
                    // Handle specific Firebase Auth exceptions
                    when (task.exception) {
                        is FirebaseAuthInvalidUserException -> {
                            errorMessage = "No account found with this email. Please sign up first."
                        }
                        else -> {
                            val errorMsg = task.exception?.message ?: "Login failed"
                            if (errorMsg.contains("password is invalid") || errorMsg.contains("wrong-password")) {
                                errorMessage = "Incorrect password. Please try again."
                            } else if (errorMsg.contains("user-not-found")) {
                                errorMessage = "No account found with this email. Please sign up first."
                            } else if (errorMsg.contains("invalid-email")) {
                                errorMessage = "Invalid email format. Please check your email."
                            } else if (errorMsg.contains("user-disabled")) {
                                errorMessage = "This account has been disabled. Please contact support."
                            } else {
                                errorMessage = errorMsg
                            }
                        }
                    }
                }
            }
    }

    // Function to handle Google Sign In
    fun handleGoogleSignIn() {
        // Sign out first to ensure account picker is shown
        googleSignInClient.signOut().addOnCompleteListener {
            val intent = googleSignInClient.signInIntent
            googleLauncher.launch(intent)
        }
    }

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
                onValueChange = {
                    email = it
                    // Clear error when user starts typing
                    if (errorMessage != null) errorMessage = null
                },
                placeholder = { Text("Enter your Email...", fontWeight = FontWeight.Bold, color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = Color(0xFFB4D48B), shape = RoundedCornerShape(50)),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFB4D48B),
                    unfocusedBorderColor = Color(0xFFB4D48B),
                    focusedTextColor = Color(0xFF4C2B1C),
                    unfocusedTextColor = Color(0xFF4C2B1C),
                    cursorColor = Color(0xFF4C2B1C),
                    disabledTextColor = Color.Gray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Password", color = Color(0xFF4C2B1C), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    // Clear error when user starts typing
                    if (errorMessage != null) errorMessage = null
                },
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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF91B572)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            errorMessage?.let { message ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = message,
                        color = Color(0xFFD32F2F),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = { handleEmailSignIn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C2B1C)),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Signing In...", color = Color.White, fontSize = 20.sp)
                } else {
                    Text("Sign In", color = Color.White, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painterResource(id = R.drawable.planme_arrow),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SocialButton(icon = R.drawable.facebook, enabled = !isLoading) { /* TODO */ }
                SocialButton(icon = R.drawable.google, enabled = !isLoading) {
                    handleGoogleSignIn()
                }
                SocialButton(icon = R.drawable.instagram, enabled = !isLoading) { /* TODO */ }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Skip For Now",
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = if (isLoading) Color.Gray else Color(0xFFFF9021),
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(enabled = !isLoading) {
                        navHostController.navigate(Routes.ConnectDevicesScreen)
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Don't have an account? ", color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text("Sign Up",
                    color = if (isLoading) Color.Gray else Color(0xFFFF9021),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable(enabled = !isLoading) {
                        navHostController.navigate(Routes.SignUpScreen)
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Forgot Password",
                color = if (isLoading) Color.Gray else Color(0xFFFF9021),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(enabled = !isLoading) {
                        // TODO: Navigate to forgot password screen
                    }
            )
        }
    }
}

@Composable
fun SocialButton(icon: Int, enabled: Boolean = true, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Color.Transparent, shape = CircleShape)
            .border(
                1.dp,
                if (enabled) Color.LightGray else Color.Gray.copy(alpha = 0.5f),
                shape = CircleShape
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            alpha = if (enabled) 1f else 0.5f
        )
    }
}