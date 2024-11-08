package com.example.myapitest.ui.viewModel

import android.annotation.SuppressLint
import android.location.Location
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapitest.data.api.RetrofitClient
import com.example.myapitest.data.model.Car
import com.example.myapitest.data.model.Place
import com.example.myapitest.data.repository.CarRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

class DetailsCarViewModel(
    private val repository: CarRepository = CarRepository(apiService = RetrofitClient.apiService),
) : ViewModel() {
    private val _car = MutableLiveData(Car("", "", "", "", "", Place(0.0, 0.0)))
    val car: LiveData<Car> get() = _car

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _selectedImageUri = MutableLiveData<Uri?>(null)
    val selectedImageUri: LiveData<Uri?> = _selectedImageUri

    private val _currentLocation = MutableLiveData<Place>()
    val currentLocation: LiveData<Place> get() = _currentLocation

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                _currentLocation.postValue(Place(it.latitude, it.longitude))
            }
        }
    }

    suspend fun fetchCarById(id: String) {
        _isLoading.value = true

        viewModelScope.launch {
            val car = repository.getCarById(id)
            _car.value = car
            if (car != null && car.imageUrl.isNotEmpty()) {
                _selectedImageUri.value = Uri.parse(car.imageUrl)
            }
             _isLoading.value = false
        }

    }

    fun editCar(car: Car) {
        _isLoading.value = true

        viewModelScope.launch {
            repository.editCar(car)
        }
        _isLoading.value = false
    }

    fun deleteCar(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.deleteCar(id)
        }
        _isLoading.value = false
    }

    fun onImageSelected(uri: Uri) {
        _selectedImageUri.value = uri
    }

    fun updateCarName(name: String) {
        _car.value = _car.value?.copy(name = name)
    }

    fun updateCarYear(year: String) {
        _car.value = _car.value?.copy(year = year)
    }

    fun updateCarLicence(licence: String) {
        _car.value = _car.value?.copy(licence = licence)
    }

    fun updateCarLocation(place: Place) {
        _currentLocation.value = place
        _car.value = _car.value?.copy(place = place)
    }
}