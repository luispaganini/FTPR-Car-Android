import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapitest.data.model.Car
import com.example.myapitest.data.model.Place
import com.example.myapitest.ui.components.FormCar
import com.example.myapitest.ui.components.LoadingGif
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddCarScreen(
    navController: NavController,
    viewModel: AddCarViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentLocation by viewModel.currentLocation.observeAsState()
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val car by viewModel.car.observeAsState(
        Car("", "", "", "", "", Place(0.0, 0.0))
    )
    val isLoading by viewModel.isLoading.observeAsState(false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Carro") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        if (isLoading) {
            LoadingGif()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),

                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                CarFormContent(
                    modifier = Modifier.padding(innerPadding),
                    car = car,
                    currentLocation = currentLocation,
                    fusedLocationClient = fusedLocationClient,
                    viewModel = viewModel,
                    onSaveClick = {
                        viewModel.addCar(car)
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CarFormContent(
    modifier: Modifier = Modifier,
    car: Car,
    currentLocation: Place?,
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: AddCarViewModel,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        val selectedImageUri by viewModel.selectedImageUri.observeAsState()
        FormCar(
            currentLocation = currentLocation,
            fusedLocationClient = fusedLocationClient,
            onImageChange = { viewModel.onImageSelected(it) },
            onLocationChange = { viewModel.updateCarLocation(it) },
            onLicenceChange = { viewModel.updateCarLicence(it) },
            onNameChange = { viewModel.updateCarName(it) },
            onYearChange = { viewModel.updateCarYear(it) },
            car = car,
            fetchCurrentLocation = { viewModel.fetchCurrentLocation(it) },
            selectedImageUri = selectedImageUri,
            editableMap = true
        )

        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            onClick = onSaveClick
        ) {
            Text("Salvar Carro")
        }
    }
}
