import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapitest.R

@Composable
fun ImagePickerCircle(viewModel: AddCarViewModel) {
    val selectedImageUri by viewModel.selectedImageUri.observeAsState()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> uri?.let { viewModel.onImageSelected(it) } }
    )
    Box(
        modifier = Modifier
            .size(120.dp) // Tamanho do círculo
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(Color.LightGray, Color.Gray)
                )
            )
            .clickable { launcher.launch("image/*") }
    ) {
        AsyncImage(
            model = selectedImageUri ?: R.drawable.ic_camera_foreground,
            contentDescription = "Imagem selecionada",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
