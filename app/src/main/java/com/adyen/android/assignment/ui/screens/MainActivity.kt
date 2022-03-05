package com.adyen.android.assignment.ui.screens

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.adyen.android.assignment.R
import com.adyen.android.assignment.databinding.ActivityMainBinding
import com.adyen.android.assignment.ui.screens.viewmodels.MainActivityViewModel
import com.adyen.android.assignment.ui.screens.viewmodels.MainActivityViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val viewModel = getMainActivityViewModel(application)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.includedLayout.mapButton.setOnClickListener {
            showToastMessage("Map button clicked.")
        }
        binding.includedLayout.listButton.setOnClickListener {
            showToastMessage("List button clicked.")
        }
    }

    private fun getMainActivityViewModel(application: Application): MainActivityViewModel {
        val viewModelFactory = MainActivityViewModelFactory(application)

        return ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
    }

    private fun showToastMessage(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}