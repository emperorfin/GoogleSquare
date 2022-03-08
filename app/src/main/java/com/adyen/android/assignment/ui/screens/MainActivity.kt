package com.adyen.android.assignment.ui.screens

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.adyen.android.assignment.R
import com.adyen.android.assignment.databinding.ActivityMainBinding
import com.adyen.android.assignment.ui.screens.viewmodels.MainActivityViewModel
import com.adyen.android.assignment.ui.screens.viewmodels.MainActivityViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel = getMainActivityViewModel(application)

        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.includedLayout.mapButton.setOnClickListener {
            showToastMessage("Map button clicked.")

            if (navController.currentDestination?.id != R.id.mapFragment) {
                navController.navigateUp()
                navController.navigate(R.id.mapFragment)

                showToastMessage("${navController.currentDestination?.id} --- ${R.id.mapFragment}")
            }
        }
        binding.includedLayout.listButton.setOnClickListener {
            showToastMessage("List button clicked.")

            if (navController.currentDestination?.id != R.id.venuesOverviewFragment) {
                navController.navigateUp()
                navController.navigate(R.id.venuesOverviewFragment)

                showToastMessage("${navController.currentDestination?.id} --- ${R.id.venuesOverviewFragment}")
            }
        }
    }

    private fun getMainActivityViewModel(application: Application): MainActivityViewModel {
        val viewModelFactory = MainActivityViewModelFactory(application)

        return ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
    }

    private fun showToastMessage(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}