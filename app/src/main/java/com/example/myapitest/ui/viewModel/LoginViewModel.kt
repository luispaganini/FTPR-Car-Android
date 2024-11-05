package com.example.myapitest.ui.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapitest.R
import com.example.myapitest.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
) : ViewModel() {

    var isOtpValid by mutableStateOf<Boolean?>(null)
        private set
    var isGoogleLoginSuccessful by mutableStateOf<Boolean?>(null)
        private set
    var isGitHubLoginSuccessful by mutableStateOf<Boolean?>(null)
        private set

    // Solicita OTP para o número de telefone (simulação)
    fun requestOtp(phoneNumber: String) {
        // Implementação de envio de OTP
    }

    // Verifica o OTP inserido pelo usuário
    fun verifyOtp(phone: String, otp: String) {
        viewModelScope.launch {
            isOtpValid = authRepository.loginWithPhone(phone, otp)
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            authRepository.firebaseAuthWithGoogle(account, onResult)
            isGoogleLoginSuccessful = true
        }
    }


    // Realiza login com GitHub (simulação)
    fun onGitHubLogin() {
        viewModelScope.launch {
            authRepository.loginWithGitHub()
            isGitHubLoginSuccessful = true
        }
    }

    // Checa se o usuário já está autenticado
    fun checkUserAuthentication(): Boolean {
        return authRepository.isUserAuthenticated()
    }
}
