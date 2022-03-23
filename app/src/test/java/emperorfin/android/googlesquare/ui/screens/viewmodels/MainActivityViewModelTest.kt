package emperorfin.android.googlesquare.ui.screens.viewmodels

import android.app.Application
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import emperorfin.android.googlesquare.ui.extentions.getOrAwaitValue
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.location.Location
import emperorfin.android.googlesquare.R


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 14th March, 2022.
 */


/**
 * Unit tests for [MainActivityViewModel]
 *
 * NOTE
 * ====
 * Running tests on Android API level >= 29 using Robolectric requires Java 9. To run such
 * Robolectric tests without configuring Android Studio to use Java 9, the target and compile SDK
 * could be kept at 28.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityViewModelTest {

    // Subject under test
    private lateinit var mMainActivityViewModel: MainActivityViewModel

    private lateinit var applicationContext: Application

    private lateinit var mView: View

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        applicationContext = ApplicationProvider.getApplicationContext()

        mMainActivityViewModel = MainActivityViewModel(applicationContext)

        mView = View(applicationContext)
    }

    //Just to confirm that the Hamcrest library is properly setup.
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)

        assertThat(4, `is`(2 + 2))
    }

    @Test
    fun openVenuesScreen_setsOpenVenuesScreenEvent() {
        // Given a fresh ViewModel
//        val mainActivityViewModel = MainActivityViewModel(ApplicationProvider.getApplicationContext())

        // When opening Venues screen
        mMainActivityViewModel.openVenuesScreen(mView)

        // Then the open Venues screen event is triggered
        val eventData = mMainActivityViewModel.openVenuesScreenEvent.getOrAwaitValue()

        assertThat(eventData.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun openMapScreen_setsOpenMapScreenEvent() {
        // When opening Map screen
        mMainActivityViewModel.openMapScreen(mView)

        // Then the open Map screen event is triggered
        val eventData = mMainActivityViewModel.openMapScreenEvent.getOrAwaitValue()

        assertThat(eventData.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun emitCurrentLocationLiveData_setsCurrentLocationLiveData_currentLocationIsNotNull() {
        // When emitting current location
        val currentLocation = Location("")
        currentLocation.longitude = 12.34
        currentLocation.longitude = 56.78
        mMainActivityViewModel.emitCurrentLocationLiveData(currentLocation)

        // Then the current location LiveData is triggered
        val resultData = mMainActivityViewModel.currentLocationLiveData.getOrAwaitValue()

        assertThat(resultData.latitude, `is`(currentLocation.latitude))
        assertThat(resultData.longitude, `is`(currentLocation.longitude))
    }

    @Test
    fun emitCurrentLocationLiveData_setsCurrentLocationLiveData_currentLocationIsNull() {
        // When emitting current location
        val currentLocation: Location? = null
        mMainActivityViewModel.emitCurrentLocationLiveData(currentLocation)

        // Then the current location LiveData is triggered
        val resultData = mMainActivityViewModel.currentLocationLiveData.getOrAwaitValue()

        assertThat(resultData, `is`(nullValue()))
    }

    @Test
    fun showComingSoonMessageToast_correctMessageIsToasted() {
        // Given an actual message.
        val actualMessage = R.string.message_coming_soon_add_custom_location

        // When showing toast message
        val toastMessage = mMainActivityViewModel.showComingSoonMessageToast(R.string.message_coming_soon_add_custom_location)

        // Then message toasted.
        assertThat(toastMessage, `is`(actualMessage))
    }

}