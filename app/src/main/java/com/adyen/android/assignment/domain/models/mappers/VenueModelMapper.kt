package com.adyen.android.assignment.domain.models.mappers

import com.adyen.android.assignment.data.datasources.local.frameworks.room.entities.VenueEntity
import com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.models.venue.VenueDataTransferObject
import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.models.VenueModel.Companion.VAR_ARG_SIZE
import com.adyen.android.assignment.domain.models.VenueModel.Companion.VENUE_EXTRAS_MAP_SIZE_UI_MODEL
import com.adyen.android.assignment.ui.uimodels.VenueUiModel
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_0
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_1
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_2
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_3
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_4
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_5
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_6
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VENUE_EXTRAS_KEY_DISTANCE
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VENUE_EXTRAS_KEY_LATITUDE
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VENUE_EXTRAS_KEY_LONGITUDE


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


class VenueModelMapper {

    companion object {

        fun verifyUiModelVenueExtras(venueExtras: Map<String, Any>): Map<String, Any> {
            if (venueExtras.size < VENUE_EXTRAS_MAP_SIZE_UI_MODEL)
                throw IllegalArgumentException(
                    "$venueExtras size (${venueExtras.size}) must not be less than " +
                            "$VENUE_EXTRAS_MAP_SIZE_UI_MODEL."
                )

            if (!venueExtras.containsKey(VENUE_EXTRAS_KEY_DISTANCE))
                throw IllegalArgumentException(
                    "$venueExtras Map must contain key: \"$VENUE_EXTRAS_KEY_DISTANCE\"."
                )

            if (venueExtras.get(VENUE_EXTRAS_KEY_DISTANCE) !is Int)
                throw IllegalArgumentException(
                    "$venueExtras Map value with the key \"$VENUE_EXTRAS_KEY_DISTANCE\" must be of " +
                            "type Int."
                )

            if (!venueExtras.containsKey(VENUE_EXTRAS_KEY_LATITUDE))
                throw IllegalArgumentException(
                    "$venueExtras Map must contain key: \"$VENUE_EXTRAS_KEY_LATITUDE\"."
                )

            if (venueExtras.get(VENUE_EXTRAS_KEY_LATITUDE) !is Double)
                throw IllegalArgumentException(
                    "$venueExtras Map value with the key \"$VENUE_EXTRAS_KEY_LATITUDE\" must be of " +
                            "type Double."
                )

            if (!venueExtras.containsKey(VENUE_EXTRAS_KEY_LONGITUDE))
                throw IllegalArgumentException(
                    "$venueExtras Map must contain key: \"$VENUE_EXTRAS_KEY_LONGITUDE\"."
                )

            if (venueExtras.get(VENUE_EXTRAS_KEY_LONGITUDE) !is Double)
                throw IllegalArgumentException(
                    "$venueExtras Map value with the key \"$VENUE_EXTRAS_KEY_LONGITUDE\" must be of " +
                            "type Double."
                )

            return venueExtras
        }

        fun verifyVenueSampleData(vararg venueSampleData: Any): Array<out Any> {
            if (venueSampleData.size < VAR_ARG_SIZE)
                throw IllegalArgumentException(
                    "$venueSampleData size must not be less than $VAR_ARG_SIZE"
                )

            if (venueSampleData[VAR_ARG_INDEX_0] !is String)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_0 in $venueSampleData must be of type String."
                )

            if (venueSampleData[VAR_ARG_INDEX_1] !is String)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_1 in $venueSampleData must be of type String."
                )

            if (venueSampleData[VAR_ARG_INDEX_2] !is String)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_2 in $venueSampleData must be of type String."
                )

            if (venueSampleData[VAR_ARG_INDEX_3] !is String)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_3 in $venueSampleData must be of type String."
                )

            if (venueSampleData[VAR_ARG_INDEX_4] !is Int)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_4 in $venueSampleData must be of type Int."
                )

            if (venueSampleData[VAR_ARG_INDEX_5] !is Double)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_5 in $venueSampleData must be of type Double."
                )

            if (venueSampleData[VAR_ARG_INDEX_6] !is Double)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_6 in $venueSampleData must be of type Double."
                )

            return venueSampleData
        }

    }

    fun transform(dataTransferObjectVenue: VenueDataTransferObject): VenueModel =
        VenueModel.newInstance(dataTransferObjectVenue)

    fun transform(entityVenue: VenueEntity): VenueModel =
        VenueModel.newInstance(entityVenue)

    fun transform(uiModelVenue: VenueUiModel): VenueModel {
        val venueExtras: Map<String, Any> = getVenueExtras(uiModelVenue)

        return VenueModel.newInstance(uiModelVenue, venueExtras)
    }

    private fun getVenueExtras(uiModelVenue: VenueUiModel): Map<String, Any>{
        val distance: Int = uiModelVenue.distance.toInt()
        val latitude: Double = uiModelVenue.latitude.toDouble()
        val longitude: Double = uiModelVenue.longitude.toDouble()

        return mapOf<String, Any>(
            VENUE_EXTRAS_KEY_DISTANCE to distance,
            VENUE_EXTRAS_KEY_LATITUDE to latitude,
            VENUE_EXTRAS_KEY_LONGITUDE to longitude
        )
    }
}