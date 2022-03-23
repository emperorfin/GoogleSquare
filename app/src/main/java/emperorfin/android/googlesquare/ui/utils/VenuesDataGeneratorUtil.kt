package emperorfin.android.googlesquare.ui.utils

import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entities.VenueEntity
import emperorfin.android.googlesquare.domain.models.mappers.VenueModelMapper
import emperorfin.android.googlesquare.ui.uimodels.VenueUiModel
import emperorfin.android.googlesquare.ui.uimodels.mappers.VenueUiModelMapper
import emperorfin.android.googlesquare.data.utils.VenuesDataGeneratorUtil as VenuesDataGeneratorUtil_FromDataLayer


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