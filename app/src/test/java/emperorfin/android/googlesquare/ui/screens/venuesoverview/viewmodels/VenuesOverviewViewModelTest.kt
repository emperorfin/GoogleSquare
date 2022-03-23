package emperorfin.android.googlesquare.ui.screens.venuesoverview.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entitysources.FakeVenueLocalDataSourceRoom
import emperorfin.android.googlesquare.data.datasources.remote.frameworks.retrofit.modelsources.FakeVenueRemoteDataSourceRetrofit
import emperorfin.android.googlesquare.data.repositories.NoOpVenuesOverviewRepository
import emperorfin.android.googlesquare.ui.extentions.getOrAwaitValue
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 14th March, 2022.
 */


/**
 * Unit tests for [VenuesOverviewViewModel]
 *
 * NOTE
 * ====
 * Running tests on Android API level >= 29 using Robolectric requires Java 9. To run such
 * Robolectric tests without configuring Android Studio to use Java 9, the target and compile SDK
 * could be kept at 28.
 *
 * For offline storage (Room database) and remote testing, please see the **"data"** package in this
 * **"test"** source set.
 */
@RunWith(AndroidJUnit4::class)
class VenuesOverviewViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun emitNoInternetConnectionError_setsNoInternetConnectionError_noInternetConnectionErrorIsNotNull() {
        // Given a fresh ViewModel
        val venueLocalDataSourceRoom = FakeVenueLocalDataSourceRoom()
        val venueRemoteDataSourceRetrofit = FakeVenueRemoteDataSourceRetrofit()
        val venueOverviewRepository = NoOpVenuesOverviewRepository(venueLocalDataSourceRoom, venueRemoteDataSourceRetrofit)
        val venuesOverviewViewModel = VenuesOverviewViewModel(ApplicationProvider.getApplicationContext(), venueOverviewRepository)

        // When emitting no internet connection results error.
        val noInternetConnectionError = VenuesOverviewViewModel.ERROR_CODE_NO_INTERNET_CONNECTION
        venuesOverviewViewModel.emitNoInternetConnectionError(noInternetConnectionError)

        // Then the no internet connection results error LiveData is triggered
        val resultData = venuesOverviewViewModel.noInternetConnectionError.getOrAwaitValue()

        assertThat(resultData, `is`(noInternetConnectionError))
    }

}