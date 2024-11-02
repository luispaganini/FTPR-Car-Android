package com.example.myapitest.data.repository

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
) {
    suspend fun loginWithPhone(phone: String, otp: String): Boolean {
        return true
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount, onResult: (Boolean) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    suspend fun loginWithGitHub() {
        // Implementação de autenticação via GitHub
    }

    fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }
}
