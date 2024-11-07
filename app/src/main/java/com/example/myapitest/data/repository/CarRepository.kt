package com.example.myapitest.data.repository

import android.net.Uri
import android.util.Log
import com.example.myapitest.data.api.ApiService
import com.example.myapitest.data.api.safeApiCall
import com.example.myapitest.data.model.Car
import com.example.myapitest.data.api.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    fun addCar(car: Car) {

    }
}
