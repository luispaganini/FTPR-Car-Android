import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LoginViewModel : ViewModel() {
    var isOtpValid by mutableStateOf<Boolean?>(null)

    fun onGoogleLogin() {
        // Lógica para login com Google
    }

    fun onGitHubLogin() {
        // Lógica para login com GitHub
    }

    fun requestOtp(phoneNumber: String) {
        // Implementar lógica para solicitar o OTP
    }

    fun verifyOtp(otpCode: String) {
        // Implementar lógica para verificar o código OTP
        // Simulação para o exemplo:
        isOtpValid = otpCode == "123456" // Substitua por verificação real
    }
}
