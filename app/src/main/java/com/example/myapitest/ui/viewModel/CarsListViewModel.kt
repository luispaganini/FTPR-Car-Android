package com.example.myapitest.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapitest.data.api.RetrofitClient
import com.example.myapitest.data.model.Car
import com.example.myapitest.data.repository.CarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarsListViewModel(
    private val carRepository: CarRepository = CarRepository(apiService = RetrofitClient.apiService)
) : ViewModel() {
    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val cars: StateFlow<List<Car>> = _cars

    init {
        fetchCars()
    }

    fun fetchCars() {
        viewModelScope.launch {
            carRepository.getCars()?.let {
                _cars.value = it
            }
        }
    }
}