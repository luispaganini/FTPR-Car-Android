import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import android.Manifest
import androidx.compose.ui.platform.LocalContext
import com.example.myapitest.R
import com.example.myapitest.data.model.Car
import com.example.myapitest.data.model.Place
import com.example.myapitest.ui.components.GoogleMapView
import com.example.myapitest.ui.components.LoadingGif
import com.example.myapitest.ui.components.RequestPermission
import com.google.android.gms.location.LocationServices

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

    Scaffold {
        if (isLoading) {
            LoadingGif()
        } else {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    ImagePickerCircle(viewModel = viewModel)
                }
                OutlinedTextField(
                    value = car.name,
                    onValueChange = { viewModel.updateCarName(it) },
                    label = { Text(text = stringResource(R.string.name_car)) },
                    modifier = Modifier.fillMaxWidth()

                )

                OutlinedTextField(
                    value = car.year,
                    onValueChange = { viewModel.updateCarYear(it) },
                    label = { Text(text = stringResource(R.string.year_car)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = car.licence,
                    onValueChange = { viewModel.updateCarLicence(it) },
                    label = { Text(text = stringResource(R.string.license_car)) },
                    modifier = Modifier.fillMaxWidth()
                )

                RequestPermission(
                    permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    rationaleMessage = stringResource(R.string.get_location_message),
                    onPermissionGranted = {
                        viewModel.fetchCurrentLocation(fusedLocationClient)
                    })

                currentLocation?.let { it ->
                    GoogleMapView(
                        it.lat,
                        it.lng
                    ) { place ->
                        viewModel.updateCarLocation(Place(place.latitude, place.longitude))
                    }
                }

                Button(
                    onClick = {
                        viewModel.addCar(car)
                    }
                ) {
                    Text("Salvar Carro")
                }
            }
        }
    }
}