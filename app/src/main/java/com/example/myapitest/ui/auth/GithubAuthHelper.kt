package com.example.myapitest.ui.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.AuthResult

object GitHubAuthHelper {
    private const val GITHUB_PROVIDER = "github.com"
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun initiateGitHubLogin(
        context: Context,
        navController: NavController
    ) {
        val provider = OAuthProvider.newBuilder(GITHUB_PROVIDER)

        firebaseAuth.startActivityForSignInWithProvider(context as Activity, provider.build())
            .addOnSuccessListener {
                handleGitHubSignInResult(context, navController)
            }
            .addOnFailureListener { exception ->
                Log.e("GitHubAuth", "Authentication failed", exception)
                Toast.makeText(context, "GitHub login failed", Toast.LENGTH_SHORT).show()
            }
    }

    fun handleGitHubSignInResult(
        context: Context,
        navController: NavController
    ) {
        val pendingResultTask = firebaseAuth.pendingAuthResult
        pendingResultTask?.addOnSuccessListener { authResult: AuthResult ->
            val user = authResult.user
            user?.let {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }?.addOnFailureListener {
            Toast.makeText(context, "GitHub login failed", Toast.LENGTH_SHORT).show()
        }
    }
}
