package com.adyen.android.assignment.api.model

data class VenueRecommendationsResponse(
    val groups: List<VenueRecommendationGroup>,
    val headerFullLocation: String,
    val headerLocation: String,
    val headerLocationGranularity: String,
    val suggestedBounds: SuggestedBounds,
    val suggestedRadius: Int,
    val totalResults: Int,
    val warning: Warning
)
