package com.example.myapitest.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapitest.R
import com.example.myapitest.data.model.Car
import com.example.myapitest.data.model.Place
import com.example.myapitest.ui.components.FormCar
import com.example.myapitest.ui.components.LoadingGif
import com.example.myapitest.ui.viewModel.DetailsCarViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsCarScreen(
    navController: NavController,
    viewModel: DetailsCarViewModel = viewModel(),
    carId: String
) {
    val context = LocalContext.current
    val currentLocation by viewModel.currentLocation.observeAsState()
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val car by viewModel.car.observeAsState(
        Car("", "", "", "", "", Place(0.0, 0.0))
    )
    val isLoading by viewModel.isLoading.observeAsState(false)

    LaunchedEffect(carId) {
        viewModel.fetchCarById(carId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.car)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back
                            )
                        )
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
                modifier = Modifier.padding(innerPadding)
            ) {
                CarFormContent(
                    car = car,
                    currentLocation = currentLocation,
                    fusedLocationClient = fusedLocationClient,
                    viewModel = viewModel,
                    onEditClick = {
                        viewModel.editCar(car)
                        redirectHome(navController)
                    },
                    onDeleteClick = {
                        viewModel.deleteCar(carId)
                        redirectHome(navController)
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
    viewModel: DetailsCarViewModel,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,

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

        Row {
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .weight(1f),
                onClick = onDeleteClick,
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = Color.White,
                    disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onErrorContainer
                )

            ) {
                Text(stringResource(R.string.delete_car))
            }

            Button(
                modifier = Modifier
                    .padding(top = 16.dp, start = 8.dp)
                    .weight(1f),
                onClick = onEditClick,
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentColor = Color.White,
                    disabledContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    disabledContentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}

private fun redirectHome(navController: NavController) {
    navController.navigate("home") {
        popUpTo("home") { inclusive = true }
    }
}