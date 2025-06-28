package com.mehmaancoders.planme.presentation.authentication

import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun rememberAuthState(): AuthState {
    var authState by remember { mutableStateOf<AuthState>(AuthState.Loading) }

    DisposableEffect(Unit) {
        val auth = FirebaseAuth.getInstance()

        // Check current user immediately
        val currentUser = auth.currentUser
        authState = if (currentUser != null) {
            AuthState.Authenticated(currentUser)
        } else {
            AuthState.Unauthenticated
        }

        // Listen for auth state changes
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            authState = if (user != null) {
                AuthState.Authenticated(user)
            } else {
                AuthState.Unauthenticated
            }
        }

        auth.addAuthStateListener(authStateListener)

        // Cleanup listener when composable is disposed
        onDispose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    return authState
}

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val user: FirebaseUser) : AuthState()
}