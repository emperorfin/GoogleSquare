package com.adyen.android.assignment.ui.utils

import com.adyen.android.assignment.data.datasources.local.frameworks.room.entities.VenueEntity
import com.adyen.android.assignment.domain.models.mappers.VenueModelMapper
import com.adyen.android.assignment.ui.uimodels.VenueUiModel
import com.adyen.android.assignment.ui.uimodels.mappers.VenueUiModelMapper
import com.adyen.android.assignment.data.utils.VenuesDataGeneratorUtil as VenuesDataGeneratorUtil_FromDataLayer


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


object VenuesDataGeneratorUtil {

    fun getVenues(): ArrayList<VenueUiModel> = transform()

    private fun transform(): ArrayList<VenueUiModel> {
        val venues: List<VenueEntity> =
            VenuesDataGeneratorUtil_FromDataLayer.getVenueEntityArrayListSampleData()

        val venueModelMapper = VenueModelMapper()
        val venueUiModelMapper = VenueUiModelMapper()

        return venues.map {
            venueModelMapper.transform(it)
        }.map {
            venueUiModelMapper.transform(it)
        } as ArrayList<VenueUiModel>
    }

}