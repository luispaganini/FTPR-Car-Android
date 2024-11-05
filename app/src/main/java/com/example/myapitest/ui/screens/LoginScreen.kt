package com.example.myapitest.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapitest.R
import com.example.myapitest.ui.components.AuthButton
import com.example.myapitest.ui.viewmodel.LoginViewModel
import com.example.myapitest.ui.auth.GoogleAuthHelper

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = LoginViewModel()
) {
    var phoneNumber by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var isOtpRequested by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        GoogleAuthHelper.handleGoogleSignInResult(result, viewModel, context, navController)
    }

    if (viewModel.isOtpValid == true) {
        navController.navigate("home") {
            popUpTo("login") { inclusive = true }
        }
    }

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Faça login", fontSize = 24.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(24.dp))

                if (!isOtpRequested) {
                    PhoneNumberInput(phoneNumber) { phoneNumber = it }
                    Spacer(modifier = Modifier.height(16.dp))
                    SendOtpButton(phoneNumber) {
                        isOtpRequested = true
                        viewModel.requestOtp(phoneNumber)
                    }
                } else {
                    OtpInput(otpCode) { otpCode = it }
                    Spacer(modifier = Modifier.height(16.dp))
                    VerifyOtpButton(viewModel, otpCode)
                    VerificationMessage(viewModel)
                }

                Spacer(modifier = Modifier.weight(1f))
                AuthButtonSection(viewModel, context, googleSignInLauncher)
            }
        }
    )
}

@Composable
fun PhoneNumberInput(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Número de Telefone") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SendOtpButton(phoneNumber: String, onClick: () -> Unit) {
    Button(
        onClick = {
            if (phoneNumber.isNotBlank()) onClick()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Enviar Código OTP")
    }
}

@Composable
fun OtpInput(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Digite o Código OTP") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun VerifyOtpButton(viewModel: LoginViewModel, otpCode: String) {
    Button(
        onClick = { viewModel.verifyOtp("", otpCode) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Verificar Código")
    }
}

@Composable
fun VerificationMessage(viewModel: LoginViewModel) {
    if (viewModel.isOtpValid == true) {
        Text(
            text = "Código verificado com sucesso!",
            color = Color.Green,
            modifier = Modifier.padding(top = 8.dp)
        )
    } else if (viewModel.isOtpValid == false) {
        Text(
            text = "Código OTP inválido.",
            color = Color.Red,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun AuthButtonSection(
    viewModel: LoginViewModel,
    context: Context,
    googleSignInLauncher: ActivityResultLauncher<Intent>
) {
    AuthButton(
        onClick = { viewModel.onGitHubLogin() },
        text = "Login com GitHub",
        iconResId = R.drawable.ic_github,
        backgroundColor = Color.Black,
        textColor = Color.White
    )

    Spacer(modifier = Modifier.height(12.dp))

    AuthButton(
        onClick = { GoogleAuthHelper.initiateGoogleLogin(context, googleSignInLauncher) },
        text = "Login com Google",
        iconResId = R.drawable.ic_google,
        textColor = Color.Black,
        backgroundColor = Color.White
    )

    Spacer(modifier = Modifier.height(24.dp))
}
