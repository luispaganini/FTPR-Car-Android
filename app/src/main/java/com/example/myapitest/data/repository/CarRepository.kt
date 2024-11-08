package com.example.myapitest.data.repository

import android.net.Uri
import android.util.Log
import com.example.myapitest.data.api.ApiService
import com.example.myapitest.data.api.safeApiCall
import com.example.myapitest.data.model.Car
import com.example.myapitest.data.api.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class CarRepository(
    private val apiService: ApiService
) {
    suspend fun getCars(): List<Car>? = withContext(Dispatchers.IO) {
        val result = safeApiCall {
            apiService.getCars()
        }
        when (result) {
            is Result.Success -> result.data
            is Result.Error -> {
                Log.e("CarRepository", "Error fetching cars: ${result.message}")
                null
            }
        }
    }

    fun uploadImage(imageUri: Uri) {
        // Lógica para enviar a imagem para o Firebase Storage
    }

    suspend fun addCar(car: Car) {
        val result = safeApiCall {
            val car2 = car.copy(
                id = UUID.randomUUID().toString(),
                imageUrl = "https://example.com/car4.jpg"
            )
            apiService.addCar(car2)
        }
        when (result) {
            is Result.Success -> {
                Log.d("CarRepository", "Car added successfully")
            }

            is Result.Error -> {
                Log.e("CarRepository", "Error adding car: ${result.message}")
            }
        }
    }

    suspend fun editCar(car: Car) {
        val result = safeApiCall {
            apiService.editCar(car.id, car)
        }

        when (result) {
            is Result.Success -> {
                Log.d("CarRepository", "Car edited successfully")
            }

            is Result.Error -> {
                Log.e("CarRepository", "Error editing car: ${result.message}")
            }
        }
    }

    suspend fun deleteCar(id: String) {
        val result = safeApiCall {
            apiService.deleteCar(id)
        }
        when (result) {
            is Result.Success -> {
                Log.d("CarRepository", "Car deleted successfully")
            }
            is Result.Error -> {
                Log.e("CarRepository", "Error deleting car: ${result.message}")
            }
        }
    }

    suspend fun getCarById(id: String): Car? {
        val result = safeApiCall {
            apiService.getCars().find { it.id == id }
        }

        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> {
                Log.e("CarRepository", "Error fetching car by ID: ${result.message}")
                null
            }
        }
    }
}