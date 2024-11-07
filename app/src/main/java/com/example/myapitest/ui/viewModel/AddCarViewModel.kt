import android.annotation.SuppressLint
import android.location.Location
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapitest.data.api.RetrofitClient
import com.example.myapitest.data.model.Car
import com.example.myapitest.data.model.Place
import com.example.myapitest.data.repository.CarRepository
import com.google.android.gms.location.FusedLocationProviderClient

class AddCarViewModel(
    private val repository: CarRepository = CarRepository(apiService = RetrofitClient.apiService),
): ViewModel() {

    private val _car = MutableLiveData(Car("", "", "", "", "", Place(0.0, 0.0)))
    val car: LiveData<Car> get() = _car

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _selectedImageUri = MutableLiveData<Uri?>(null)
    val selectedImageUri: LiveData<Uri?> = _selectedImageUri

    private val _currentLocation = MutableLiveData<Place>()
    val currentLocation: LiveData<Place> get() = _currentLocation

    fun addCar(car: Car) {
        _isLoading.value = true
        // Repositório para salvar os dados no Firebase
        repository.addCar(car)
        _isLoading.value = false
    }

    fun onImageSelected(uri: Uri) {
        _selectedImageUri.value = uri
    }

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                _currentLocation.postValue(Place(it.latitude, it.longitude))
            }
        }
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
