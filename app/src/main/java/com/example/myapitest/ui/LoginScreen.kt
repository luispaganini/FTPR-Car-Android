package com.example.myapitest.ui

import LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapitest.R
import com.example.myapitest.ui.components.AuthButton

@Composable
fun LoginScreen(viewModel: LoginViewModel = LoginViewModel()) {
    var phoneNumber by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var isOtpRequested by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(), // Preencher a altura total
                verticalArrangement = Arrangement.SpaceBetween, // Espalhar o conteúdo
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Faça login", fontSize = 24.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(24.dp))

                if (!isOtpRequested) {
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Número de Telefone") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (phoneNumber.isNotBlank()) {
                                isOtpRequested = true
                                viewModel.requestOtp(phoneNumber)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Enviar Código OTP")
                    }
                } else {
                    OutlinedTextField(
                        value = otpCode,
                        onValueChange = { otpCode = it },
                        label = { Text("Digite o Código OTP") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.verifyOtp(otpCode)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Verificar Código")
                    }

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

                Spacer(modifier = Modifier.weight(1f))

                AuthButton(
                    onClick = { viewModel.onGitHubLogin() },
                    text = "Login com GitHub",
                    iconResId = R.drawable.ic_github,
                    backgroundColor = Color.Black,
                    textColor = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                AuthButton(
                    onClick = { viewModel.onGitHubLogin() },
                    text = "Login com Google",
                    iconResId = R.drawable.ic_google,
                    textColor = Color.Black,
                    backgroundColor = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    )
}
