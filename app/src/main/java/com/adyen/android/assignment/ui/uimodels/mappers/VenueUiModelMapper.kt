package com.adyen.android.assignment.ui.uimodels.mappers

import com.adyen.android.assignment.domain.models.VenueModel
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
import com.adyen.android.assignment.ui.uimodels.VenueUiModel
import com.adyen.android.assignment.ui.uimodels.VenueUiModel.Companion.VAR_ARG_SIZE
import com.adyen.android.assignment.ui.uimodels.VenueUiModel.Companion.VENUE_EXTRAS_MAP_SIZE_DOMAIN_MODEL


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


class VenueUiModelMapper {

    companion object {

        fun verifyDomainModelVenueExtras(venueExtras: Map<String, Any>): Map<String, Any> {
            if (venueExtras.size < VENUE_EXTRAS_MAP_SIZE_DOMAIN_MODEL)
                throw IllegalArgumentException("$venueExtras size (${venueExtras.size}) must not be less than $VENUE_EXTRAS_MAP_SIZE_DOMAIN_MODEL.")

            if (!venueExtras.containsKey(VENUE_EXTRAS_KEY_DISTANCE))
                throw IllegalArgumentException("$venueExtras Map must contain key: \"$VENUE_EXTRAS_KEY_DISTANCE\".")

            if (venueExtras.get(VENUE_EXTRAS_KEY_DISTANCE) !is String)
                throw IllegalArgumentException("$venueExtras Map value with the key \"$VENUE_EXTRAS_KEY_DISTANCE\" must be of type String.")

            if (!venueExtras.containsKey(VENUE_EXTRAS_KEY_LATITUDE))
                throw IllegalArgumentException("$venueExtras Map must contain key: \"$VENUE_EXTRAS_KEY_LATITUDE\".")

            if (venueExtras.get(VENUE_EXTRAS_KEY_LATITUDE) !is String)
                throw IllegalArgumentException("$venueExtras Map value with the key \"$VENUE_EXTRAS_KEY_LATITUDE\" must be of type String.")

            if (!venueExtras.containsKey(VENUE_EXTRAS_KEY_LONGITUDE))
                throw IllegalArgumentException("$venueExtras Map must contain key: \"$VENUE_EXTRAS_KEY_LONGITUDE\".")

            if (venueExtras.get(VENUE_EXTRAS_KEY_LONGITUDE) !is String)
                throw IllegalArgumentException("$venueExtras Map value with the key \"$VENUE_EXTRAS_KEY_LONGITUDE\" must be of type String.")

            return venueExtras
        }

        fun verifyVenueSampleData(vararg venueSampleData: Any): Array<out Any> {
            if (venueSampleData.size < VAR_ARG_SIZE)
                throw IllegalArgumentException("$venueSampleData size must not be less than $VAR_ARG_SIZE")

            if (venueSampleData[VAR_ARG_INDEX_0] !is String)
                throw IllegalArgumentException("Element at index $VAR_ARG_INDEX_0 in $venueSampleData must be of type String.")

            if (venueSampleData[VAR_ARG_INDEX_1] !is String)
                throw IllegalArgumentException("Element at index $VAR_ARG_INDEX_1 in $venueSampleData must be of type String.")

            if (venueSampleData[VAR_ARG_INDEX_2] !is String)
                throw IllegalArgumentException("Element at index $VAR_ARG_INDEX_2 in $venueSampleData must be of type String.")

            if (venueSampleData[VAR_ARG_INDEX_3] !is String)
                throw IllegalArgumentException("Element at index $VAR_ARG_INDEX_3 in $venueSampleData must be of type String.")

            if (venueSampleData[VAR_ARG_INDEX_4] !is String)
                throw IllegalArgumentException("Element at index $VAR_ARG_INDEX_4 in $venueSampleData must be of type String.")

            if (venueSampleData[VAR_ARG_INDEX_5] !is String)
                throw IllegalArgumentException("Element at index $VAR_ARG_INDEX_5 in $venueSampleData must be of type String.")

            if (venueSampleData[VAR_ARG_INDEX_6] !is String)
                throw IllegalArgumentException("Element at index $VAR_ARG_INDEX_6 in $venueSampleData must be of type String.")

            return venueSampleData
        }

    }

    fun transform(domainModelVenue: VenueModel): VenueUiModel {
        val venueExtras: Map<String, Any> = getVenueExtras(domainModelVenue)

        return VenueUiModel.newInstance(domainModelVenue, venueExtras)
    }

    private fun getVenueExtras(domainModelVenue: VenueModel): Map<String, Any>{
        val distance: String = domainModelVenue.distance.toString()
        val latitude: String = domainModelVenue.latitude.toString()
        val longitude: String = domainModelVenue.longitude.toString()

        return mapOf<String, Any>(
            VENUE_EXTRAS_KEY_DISTANCE to distance,
            VENUE_EXTRAS_KEY_LATITUDE to latitude,
            VENUE_EXTRAS_KEY_LONGITUDE to longitude
        )
    }
}