package com.adyen.android.assignment.api.model

data class VenueRecommendationGroup(
    val items: List<RecommendedItem>,
    val name: String,
    val type: String
)
