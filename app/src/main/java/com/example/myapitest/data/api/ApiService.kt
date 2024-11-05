package com.example.myapitest.data.api

import com.example.myapitest.data.model.Car
import retrofit2.http.GET

interface ApiService {
    @GET("car")
    suspend fun getCars(): List<Car>

}