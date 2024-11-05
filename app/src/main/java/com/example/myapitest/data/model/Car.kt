package com.example.myapitest.data.model

data class Car (
    val id: String,
    val imageUrl: String,
    val year: String,
    val name: String,
    val licence: String,
    val place: Place,
)

data class Place (
    val lat: Double,
    val lng: Double,
)