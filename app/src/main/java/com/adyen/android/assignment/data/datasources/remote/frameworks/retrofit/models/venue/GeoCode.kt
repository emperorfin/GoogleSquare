package com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.models.venue

data class GeoCode(
    val main: Main
)

data class Main(
    val latitude: Double,
    val longitude: Double,
)