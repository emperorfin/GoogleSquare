package com.adyen.android.assignment.ui.screens

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
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.adyen.android.assignment.R
import com.adyen.android.assignment.databinding.ActivityMainBinding
import com.adyen.android.assignment.ui.screens.map.MapFragmentDirections
import com.adyen.android.assignment.ui.screens.map.contracts.IMapFragmentCallbacks
import com.adyen.android.assignment.ui.screens.viewmodels.MainActivityViewModel
import com.adyen.android.assignment.ui.screens.viewmodels.MainActivityViewModelFactory


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
        mViewModel.currentLocationLiveData.observe(this, Observer {
            it?.let {
                mCurrentLocation = it

                showToastMessage("MainActivity: $it")

                mViewModel.emitCurrentLocationLiveData(null)
            }
        })

        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController

        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
        binding.includedLayout.mapButton.setOnClickListener {
            navigateToMapScreen(it)
        }
        binding.includedLayout.tvMap.setOnClickListener {
            navigateToMapScreen(it)
        }
        binding.includedLayout.listButton.setOnClickListener {
            navigateToVenueListScreen(it)
        }
        binding.includedLayout.tvVenueList.setOnClickListener {
            navigateToVenueListScreen(it)
        }
    }

    private fun getMainActivityViewModel(application: Application): MainActivityViewModel {
        val viewModelFactory = MainActivityViewModelFactory(application)

        return ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
    }

    private fun navigateToMapScreen(view: View?){
        showToastMessage("Map button clicked.")

        if (mNavController.currentDestination?.id != R.id.mapFragment) {
            showToastMessage("${mNavController.currentDestination?.id} --- ${R.id.mapFragment}")

            mNavController.navigateUp()
            mNavController.navigate(R.id.mapFragment)
        }
    }

    private fun navigateToVenueListScreen(view: View?){
        showToastMessage("List button clicked.")

        if (mNavController.currentDestination?.id != R.id.venuesOverviewFragment) {
            showToastMessage("${mNavController.currentDestination?.id} --- ${R.id.venuesOverviewFragment}")

            if (mCurrentLocation != null){
                val latitude = mCurrentLocation?.latitude!!
                val longitude = mCurrentLocation?.longitude!!

                if (latitude != null || longitude != null) {
                    mNavController.navigateUp()
//                    mNavController.navigate(R.id.venuesOverviewFragment)
                    mNavController.navigate(MapFragmentDirections.actionMapFragmentToVenuesOverviewFragment(latitude.toString(), longitude.toString()))
                } else {
                    showToastMessage("Oops! This shouldn't happen. This means you can visit the nearby venues when your current location is available.")
                }
            } else {
                showToastMessage("You can visit the nearby venues when your current location is available.")
            }
        }
    }

    private fun showToastMessage(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onCurrentLocationReady(currentLocation: Location) {
        mViewModel.emitCurrentLocationLiveData(currentLocation)
//        mCurrentLocation = currentLocation
//        showToastMessage("MainActivity: $currentLocation")
    }
}