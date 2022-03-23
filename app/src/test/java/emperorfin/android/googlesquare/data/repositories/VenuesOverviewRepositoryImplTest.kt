package emperorfin.android.googlesquare.data.repositories

import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entities.VenueEntity
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_4

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_4

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_4

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_4

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_4

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_4

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_4
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entitysources.FakeVenueLocalDataSourceRoom
import emperorfin.android.googlesquare.data.datasources.remote.frameworks.retrofit.models.venue.*
import emperorfin.android.googlesquare.data.datasources.remote.frameworks.retrofit.modelsources.FakeVenueRemoteDataSourceRetrofit
import emperorfin.android.googlesquare.domain.datalayer.datasources.FakeVenueDataSource
import emperorfin.android.googlesquare.domain.models.mappers.VenueModelMapper
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent.Success
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent.Error
import emperorfin.android.googlesquare.ui.events.inputs.venue.None
import emperorfin.android.googlesquare.ui.events.inputs.venue.VenueParams
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 15th March, 2022.
 */


/**
 * Unit tests for the implementation of the in-memory repository ([VenuesOverviewRepositoryImpl])
 * with cache.
 */
@ExperimentalCoroutinesApi
class VenuesOverviewRepositoryImplTest {

    private val icon1 = Icon(
        ICON_PREFIX_1,
        ICON_SUFFIX_1
    )

    private val icon2 = Icon(
        ICON_PREFIX_2,
        ICON_SUFFIX_2
    )

    private val icon3 = Icon(
        ICON_PREFIX_3,
        ICON_SUFFIX_3
    )

    private val category1 = Category(
        icon = icon1,
        id = "1",
        name = CATEGORY_1
    )

    private val category2 = Category(
        icon = icon2,
        id = "2",
        name = CATEGORY_2
    )

    private val category3 = Category(
        icon = icon3,
        id = "3",
        name = CATEGORY_3
    )

    private val categories1 = mutableListOf<Category>(category1)
    private val categories2 = mutableListOf<Category>(category2)
    private val categories3 = mutableListOf<Category>(category3)

    private val main1 = Main(
        LATITUDE_1,
        LONGITUDE_1
    )

    private val main2 = Main(
        LATITUDE_2,
        LONGITUDE_2
    )

    private val main3 = Main(
        LATITUDE_3,
        LONGITUDE_3
    )

    private val geocodes1 = GeoCode(main1)
    private val geocodes2 = GeoCode(main2)
    private val geocodes3 = GeoCode(main3)

    private val location1 = Location(
        "",
        "",
        "",
        listOf("", ""),
        "",
        ""
    )

    private val location2 = Location(
        "",
        "",
        "",
        listOf("", ""),
        "",
        ""
    )

    private val location3 = Location(
        "",
        "",
        "",
        listOf("", ""),
        "",
        ""
    )

    private val dataTransferObjectVenue1 = VenueDataTransferObject(
        categories = categories1,
        distance = 123,
        geocodes = geocodes1,
        location = location1,
        name = NAME_1,
        timezone = ""
    )

    private val dataTransferObjectVenue2 = VenueDataTransferObject(
        categories = categories2,
        distance = 456,
        geocodes = geocodes2,
        location = location2,
        name = NAME_2,
        timezone = ""
    )

    private val dataTransferObjectVenue3 = VenueDataTransferObject(
        categories = categories3,
        distance = 789,
        geocodes = geocodes3,
        location = location3,
        name = NAME_3,
        timezone = ""
    )

    private val entityVenue1 = VenueEntity.newInstance(
        NAME_4,
        CATEGORY_4,
        ICON_PREFIX_4,
        ICON_SUFFIX_4,
        DISTANCE_4,
        LATITUDE_4,
        LONGITUDE_4
    )

    private val remoteVenues = listOf(
        dataTransferObjectVenue1,
        dataTransferObjectVenue2,
        dataTransferObjectVenue3
    ).sortedBy {
        it.name
    }

    private val localVenues = listOf(entityVenue1).sortedBy {
        it.name
    }

    private lateinit var venueLocalDataSource: FakeVenueDataSource
    private lateinit var venueRemoteDataSource: FakeVenueDataSource

    // Class under test
    private lateinit var venuesOverviewRepository: VenuesOverviewRepositoryImpl

    @Before
    fun createRepository() {
        venueRemoteDataSource = FakeVenueRemoteDataSourceRetrofit(remoteVenues.toMutableList())
        venueLocalDataSource = FakeVenueLocalDataSourceRoom(localVenues.toMutableList())

        venuesOverviewRepository = VenuesOverviewRepositoryImpl(
            venueLocalDataSource,
            venueRemoteDataSource,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun getVenues_requestsAllVenuesFromRemoteDataSource_venuesRetrieved() = runBlockingTest {
        // When venues are requested from the venues overview repository
        val venueModelMapper = VenueModelMapper()
        val mappedModelVenues = remoteVenues.map { venueModelMapper.transform(it) }

        val paramNothing = None()
        val paraSomething = VenueParams()

        val modelVenuesDataResultEvent = venuesOverviewRepository.getVenues(
            paramNothing,
            paraSomething,
            true
        ) as Success

        // Then venues are loaded from the remote data source
        assertThat(modelVenuesDataResultEvent.data, IsEqual(mappedModelVenues))
    }

    @Test
    fun getVenues_requestsAllVenuesFromRemoteDataSource_errorEncounteredIfListEmpty() = runBlockingTest {
        // Given
        venueRemoteDataSource = FakeVenueRemoteDataSourceRetrofit()
        venueLocalDataSource = FakeVenueLocalDataSourceRoom()

        venuesOverviewRepository = VenuesOverviewRepositoryImpl(
            venueLocalDataSource,
            venueRemoteDataSource,
            Dispatchers.Unconfined
        )

        // When venues are requested from the venues overview repository
        val paramNothing = None()
        val paraSomething = VenueParams()

        val modelVenuesDataResultEvent = venuesOverviewRepository.getVenues(
            paramNothing,
            paraSomething,
            true
        )

        // Then venues are loaded from the remote data source
        assertTrue(modelVenuesDataResultEvent is Error)
    }

    @Test
    fun getVenues_requestsAllVenuesFromRemoteDataSource_errorEncounteredIfListIsNull() = runBlockingTest {
        // Given
        venueRemoteDataSource = FakeVenueRemoteDataSourceRetrofit(null)
        venueLocalDataSource = FakeVenueLocalDataSourceRoom()

        venuesOverviewRepository = VenuesOverviewRepositoryImpl(
            venueLocalDataSource,
            venueRemoteDataSource,
            Dispatchers.Unconfined
        )

        // When venues are requested from the venues overview repository
        val paramNothing = None()
        val paraSomething = VenueParams()

        val modelVenuesDataResultEvent = venuesOverviewRepository.getVenues(
            paramNothing,
            paraSomething,
            true
        )

        // Then venues are loaded from the remote data source
        assertTrue(modelVenuesDataResultEvent is Error)
    }

    @Test
    fun getVenues_requestsAllVenuesFromRemoteOrLocalDataSource_errorEncounteredIfBothListsAreNull() = runBlockingTest {
        // Given
        venueRemoteDataSource = FakeVenueRemoteDataSourceRetrofit(null)
        venueLocalDataSource = FakeVenueLocalDataSourceRoom(null)

        venuesOverviewRepository = VenuesOverviewRepositoryImpl(
            venueLocalDataSource,
            venueRemoteDataSource,
            Dispatchers.Unconfined
        )

        // When venues are requested from the venues overview repository
        val paramNothing = None()
        val paraSomething = VenueParams()

        val modelVenuesDataResultEvent = venuesOverviewRepository.getVenues(
            paramNothing,
            paraSomething,
            false
        )

        // Then venues are loaded from the remote data source
        assertTrue(modelVenuesDataResultEvent is Error)
    }

    @Test
    fun getVenues_requestsAllVenuesFromLocalDataSource_venuesRetrieved() = runBlockingTest {
        // When venues are requested from the venues overview repository
        val venueModelMapper = VenueModelMapper()
        val mappedModelVenues = localVenues.map { venueModelMapper.transform(it) }

        val paramNothing = None()

        val modelVenuesDataResultEvent = venuesOverviewRepository.venueLocalDataSource.getVenues(
            paramNothing
        ) as Success

        // Then venues are loaded from the remote data source
        assertThat(modelVenuesDataResultEvent.data, IsEqual(mappedModelVenues))
    }

    @Test
    fun getVenues_requestsAllVenuesFromLocalDataSource_errorEncounteredIfListEmpty() = runBlockingTest {
        // Given
        venueRemoteDataSource = FakeVenueRemoteDataSourceRetrofit()
        venueLocalDataSource = FakeVenueLocalDataSourceRoom()

        venuesOverviewRepository = VenuesOverviewRepositoryImpl(
            venueLocalDataSource,
            venueRemoteDataSource,
            Dispatchers.Unconfined
        )

        // When venues are requested from the venues overview repository
        val paramNothing = None()

        val modelVenuesDataResultEvent = venuesOverviewRepository.venueLocalDataSource.getVenues(
            paramNothing
        )

        // Then venues are loaded from the remote data source
        assertTrue(modelVenuesDataResultEvent is Error)
    }

    @Test
    fun getVenues_requestsAllVenuesFromLocalDataSource_errorEncounteredIfListIsNull() = runBlockingTest {
        // Given
        venueRemoteDataSource = FakeVenueRemoteDataSourceRetrofit()
        venueLocalDataSource = FakeVenueLocalDataSourceRoom(null)

        venuesOverviewRepository = VenuesOverviewRepositoryImpl(
            venueLocalDataSource,
            venueRemoteDataSource,
            Dispatchers.Unconfined
        )

        // When venues are requested from the venues overview repository
        val paramNothing = None()

        val modelVenuesDataResultEvent = venuesOverviewRepository.venueLocalDataSource.getVenues(
            paramNothing
        )

        // Then venues are loaded from the remote data source
        assertTrue(modelVenuesDataResultEvent is Error)
    }

}