package com.mehmaancoders.planme.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.mehmaancoders.planme.R
import com.mehmaancoders.planme.presentation.navigation.Routes

@Composable
fun SignUpScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Function to validate input fields
    fun validateInputs(): Boolean {
        when {
            username.trim().isEmpty() -> {
                errorMessage = "Please enter your username"
                return false
            }
            username.trim().length < 3 -> {
                errorMessage = "Username must be at least 3 characters long"
                return false
            }
            email.trim().isEmpty() -> {
                errorMessage = "Please enter your email address"
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                errorMessage = "Please enter a valid email address"
                return false
            }
            password.trim().isEmpty() -> {
                errorMessage = "Please enter your password"
                return false
            }
            password.length < 6 -> {
                errorMessage = "Password must be at least 6 characters long"
                return false
            }
            confirmPassword.trim().isEmpty() -> {
                errorMessage = "Please confirm your password"
                return false
            }
            password != confirmPassword -> {
                errorMessage = "Passwords do not match"
                return false
            }
            else -> {
                errorMessage = null
                return true
            }
        }
    }

    // Function to check if username already exists
    fun checkUsernameAvailability(username: String, onResult: (Boolean) -> Unit) {
        firestore.collection("users")
            .whereEqualTo("username", username.trim().lowercase())
            .get()
            .addOnSuccessListener { documents ->
                onResult(documents.isEmpty)
            }
            .addOnFailureListener {
                onResult(true) // Allow if check fails
            }
    }

    // Function to save user data to Firestore
    fun saveUserToFirestore(userId: String, username: String, email: String) {
        val userData = hashMapOf(
            "username" to username.trim(),
            "email" to email.trim(),
            "createdAt" to System.currentTimeMillis()
        )

        firestore.collection("users").document(userId)
            .set(userData)
            .addOnSuccessListener {
                // Update Firebase Auth profile with username
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(username.trim())
                    .build()

                auth.currentUser?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener { profileTask ->
                        isLoading = false
                        if (profileTask.isSuccessful) {
                            navHostController.navigate(Routes.ConnectDevicesScreen) {
                                popUpTo(Routes.SignUpScreen) { inclusive = true }
                            }
                        } else {
                            // Even if profile update fails, user is created successfully
                            navHostController.navigate(Routes.ConnectDevicesScreen) {
                                popUpTo(Routes.SignUpScreen) { inclusive = true }
                            }
                        }
                    }
            }
            .addOnFailureListener { e ->
                isLoading = false
                errorMessage = "Failed to save user data: ${e.message}"
            }
    }

    // Function to handle sign up
    fun handleSignUp() {
        if (!validateInputs()) return

        isLoading = true
        errorMessage = null

        // First check if username is available
        checkUsernameAvailability(username) { isAvailable ->
            if (!isAvailable) {
                isLoading = false
                errorMessage = "Username '${username.trim()}' is already taken. Please choose another one."
                return@checkUsernameAvailability
            }

            // Proceed with account creation
            auth.createUserWithEmailAndPassword(email.trim(), password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Account created successfully, now save user data
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            saveUserToFirestore(userId, username, email)
                        } else {
                            isLoading = false
                            errorMessage = "Failed to get user ID"
                        }
                    } else {
                        isLoading = false
                        // Handle specific Firebase Auth exceptions
                        when (task.exception) {
                            is FirebaseAuthUserCollisionException -> {
                                errorMessage = "An account with this email already exists. Please sign in instead."
                            }
                            else -> {
                                val errorMsg = task.exception?.message ?: "Registration failed"
                                when {
                                    errorMsg.contains("email-already-in-use") -> {
                                        errorMessage = "An account with this email already exists. Please sign in instead."
                                    }
                                    errorMsg.contains("weak-password") -> {
                                        errorMessage = "Password is too weak. Please choose a stronger password."
                                    }
                                    errorMsg.contains("invalid-email") -> {
                                        errorMessage = "Invalid email format. Please check your email."
                                    }
                                    errorMsg.contains("network") -> {
                                        errorMessage = "Network error. Please check your internet connection."
                                    }
                                    else -> {
                                        errorMessage = errorMsg
                                    }
                                }
                            }
                        }
                    }
                }
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
            text = "Sign Up For Free",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = Color(0xFF4C2B1C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            // Username Field
            Text("Username", color = Color(0xFF4C2B1C), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    // Clear error when user starts typing
                    if (errorMessage != null) errorMessage = null
                },
                placeholder = { Text("Enter your username...", fontWeight = FontWeight.Bold, color = Color.Gray) },
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
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
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
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
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

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password Field
            Text("Password Confirmation", color = Color(0xFF4C2B1C), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    // Clear error when user starts typing
                    if (errorMessage != null) errorMessage = null
                },
                placeholder = { Text("Confirm your password...", fontWeight = FontWeight.Bold, color = Color.Gray) },
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
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Confirm Password Visibility",
                            tint = Color.Gray
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Loading Indicator
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF91B572)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Error Message
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

            // Sign Up Button
            Button(
                onClick = { handleSignUp() },
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
                    Text("Creating Account...", color = Color.White, fontSize = 20.sp)
                } else {
                    Text("Sign Up", color = Color.White, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.planme_arrow),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Already have an account? ", color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text(
                    "Sign In",
                    color = if (isLoading) Color.Gray else Color(0xFFFF9021),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable(enabled = !isLoading) {
                        navHostController.navigate(Routes.SignInScreen)
                    }
                )
            }
        }
    }
}