package com.example.myapitest.data.api

import com.example.myapitest.data.model.Car
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("car")
    suspend fun getCars(): List<Car>

    @POST("car")
    suspend fun addCar(@Body car: Car)

    @PATCH("car/{id}")
    suspend fun editCar(@Path("id") id: String, @Body car: Car)

    @DELETE("car/{id}")
    suspend fun deleteCar(@Path("id") id: String)


}