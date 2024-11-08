package com.example.myapitest.ui.components

import ImagePickerCircle
import android.Manifest
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myapitest.R
import com.example.myapitest.data.model.Car
import com.example.myapitest.data.model.Place
import com.google.android.gms.location.FusedLocationProviderClient

@Composable
fun FormCar(
    currentLocation: Place?,
    fusedLocationClient: FusedLocationProviderClient,
    onNameChange: (String) -> Unit = {},
    onYearChange: (String) -> Unit = {},
    onLicenceChange: (String) -> Unit = {},
    onImageChange: (Uri) -> Unit = {},
    onLocationChange: (Place) -> Unit = {},
    car: Car,
    fetchCurrentLocation: (fusedLocationClient: FusedLocationProviderClient) -> Unit = {},
    editableMap: Boolean = true,
    selectedImageUri: Uri?
) {
    Column {
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            ImagePickerCircle(onImageChange, selectedImageUri)
        }
        OutlinedTextField(
            value = car.name,
            onValueChange = { onNameChange(it) },
            label = { Text(text = stringResource(R.string.name_car)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = car.year,
            onValueChange = { onYearChange(it) },
            label = { Text(text = stringResource(R.string.year_car)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = car.licence,
            onValueChange = { onLicenceChange(it) },
            label = { Text(text = stringResource(R.string.license_car)) },
            modifier = Modifier.fillMaxWidth()
        )

        RequestPermission(
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            rationaleMessage = stringResource(R.string.get_location_message),
            onPermissionGranted = {
                fetchCurrentLocation(fusedLocationClient)
            })

        currentLocation?.let {
            GoogleMapView(
                it.lat,
                it.long,
                onMarkerPositionChanged = { place ->
                    onLocationChange(Place(place.latitude, place.longitude))
                },
                editableMap
            )
        }
    }
}