package emperorfin.android.googlesquare.ui.screens

import android.app.Application
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import emperorfin.android.googlesquare.R
import emperorfin.android.googlesquare.databinding.ActivityMainBinding
import emperorfin.android.googlesquare.ui.screens.map.MapFragmentDirections
import emperorfin.android.googlesquare.ui.screens.map.contracts.IMapFragmentCallbacks
import emperorfin.android.googlesquare.ui.screens.viewmodels.MainActivityViewModel
import emperorfin.android.googlesquare.ui.screens.viewmodels.MainActivityViewModelFactory
import emperorfin.android.googlesquare.ui.events.outputs.EventDataImpl.EventDataImplObserver
import emperorfin.android.googlesquare.ui.screens.venuesoverview.VenuesOverviewFragmentDirections


class MainActivity : AppCompatActivity(), IMapFragmentCallbacks {

    companion object {

        const val EXTRA_BUNDLE_LATITUDE = "latitude"
        const val EXTRA_BUNDLE_LONGITUDE = "longitude"

    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var mViewModel: MainActivityViewModel

    private var mCurrentLocation: Location? = null

    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mViewModel = getMainActivityViewModel(application)

        setupLiveDataObservation()

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController

        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        setupNavigation()
    }

    private fun getMainActivityViewModel(application: Application): MainActivityViewModel {
        val viewModelFactory = MainActivityViewModelFactory(application)

        return ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
    }

    private fun setupNavigation() {
        mViewModel.openMapScreenEvent.observe(this, EventDataImplObserver<View?>{
            val view: View? = it

            navigateToMapScreen(view)
        })

        mViewModel.openVenuesScreenEvent.observe(this, EventDataImplObserver<View?>{
            val view: View? = it

            navigateToVenueListScreen(view)
        })
    }

    private fun navigateToMapScreen(view: View?){
        if (mNavController.currentDestination?.id != R.id.mapFragment) {
            mNavController.navigate(
                VenuesOverviewFragmentDirections.actionVenuesOverviewFragmentToMapFragment()
            )
        }
    }

    private fun navigateToVenueListScreen(view: View?){
        if (mNavController.currentDestination?.id != R.id.venuesOverviewFragment) {

            if (mCurrentLocation != null){
                val latitude = mCurrentLocation?.latitude!!
                val longitude = mCurrentLocation?.longitude!!

                if (latitude != null || longitude != null) {
                    mNavController.navigate(
                        MapFragmentDirections.actionMapFragmentToVenuesOverviewFragment(
                            latitude.toString(), longitude.toString()
                        )
                    )
                } else {
                    showToastMessage(
                        getString(
                            R.string.error_oops_can_only_visit_nearby_location_when_location_is_available
                        )
                    )
                }
            } else {
                showToastMessage(
                    getString(
                        R.string.error_can_only_visit_nearby_location_when_location_is_available
                    )
                )
            }
        }
    }

    private fun setupLiveDataObservation(){
        mViewModel.currentLocationLiveData.observe(this, Observer {
            it?.let {
                mCurrentLocation = it

                mViewModel.emitCurrentLocationLiveData(null)
            }
        })
    }

    private fun showToastMessage(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onCurrentLocationReady(currentLocation: Location) {
        mViewModel.emitCurrentLocationLiveData(currentLocation)
    }
}