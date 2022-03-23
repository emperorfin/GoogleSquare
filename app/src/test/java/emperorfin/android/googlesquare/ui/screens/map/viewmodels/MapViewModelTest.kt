package emperorfin.android.googlesquare.ui.screens.map.viewmodels

import android.app.Application
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import emperorfin.android.googlesquare.ui.extentions.getOrAwaitValue
import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 14th March, 2022.
 */


/**
 * Unit tests for [MapViewModel]
 *
 * NOTE
 * ====
 * Running tests on Android API level >= 29 using Robolectric requires Java 9. To run such
 * Robolectric tests without configuring Android Studio to use Java 9, the target and compile SDK
 * could be kept at 28.
 */
@RunWith(AndroidJUnit4::class)
class MapViewModelTest {

    // Subject under test
    private lateinit var mMapViewModel: MapViewModel

    private lateinit var applicationContext: Application

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        applicationContext = ApplicationProvider.getApplicationContext()

        mMapViewModel = MapViewModel(applicationContext)
    }

    @Test
    fun emitCurrentLocationResultsError_setsCurrentLocationResultsError_currentLocationResultsErrorIsNotNull() {
        // When emitting current location results error.
        val currentLocationResultsError = MapViewModel.ERROR_CODE_NO_CURRENT_LOCATION
        mMapViewModel.emitCurrentLocationResultsError(currentLocationResultsError)

        // Then the current location results error LiveData is triggered
        val resultData = mMapViewModel.currentLocationResultsError.getOrAwaitValue()

        assertThat(resultData, `is`(currentLocationResultsError))
    }

    @Test
    fun emitNoInternetConnectionError_setsNoInternetConnectionError_noInternetConnectionErrorIsNotNull() {
        // When emitting no internet connection results error.
        val noInternetConnectionError = MapViewModel.ERROR_CODE_NO_INTERNET_CONNECTION
        mMapViewModel.emitNoInternetConnectionError(noInternetConnectionError)

        // Then the no internet connection results error LiveData is triggered
        val resultData = mMapViewModel.noInternetConnectionError.getOrAwaitValue()

        assertThat(resultData, `is`(noInternetConnectionError))
    }

    @Test
    fun refreshCurrentLocation_setsCurrentLocationLiveData_currentLocationIsNotNull() {
        // When refreshing current location with non null value.
        val currentLocation = Location("")
        currentLocation.longitude = 12.34
        currentLocation.longitude = 56.78
        mMapViewModel.refreshCurrentLocation(currentLocation)

        // Then the current location LiveData is triggered
        val resultData = mMapViewModel.currentLocationLiveData.getOrAwaitValue()

        assertThat(resultData, not(nullValue()))
        assertThat(resultData.latitude, `is`(currentLocation.latitude))
        assertThat(resultData.longitude, `is`(currentLocation.longitude))
    }

    @Test
    fun refreshCurrentLocation_setsCurrentLocationResultsError_currentLocationIsNull() {
        // When refreshing current location with null value.
        val currentLocation = null
        mMapViewModel.refreshCurrentLocation(currentLocation)

        // Then the current location results error is triggered
        val resultData = mMapViewModel.currentLocationResultsError.getOrAwaitValue()

        assertThat(resultData, `is`(MapViewModel.ERROR_CODE_NO_CURRENT_LOCATION))
    }

}