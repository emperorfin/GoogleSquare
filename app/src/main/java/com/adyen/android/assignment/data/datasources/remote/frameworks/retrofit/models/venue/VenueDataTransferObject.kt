package com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.models.venue

data class VenueDataTransferObject(
    val categories: List<Category>,
    val distance: Int,
    val geocode: GeoCode,
    val location: Location,
    val name: String,
    val timezone: String,
)