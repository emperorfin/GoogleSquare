package com.adyen.android.assignment.ui.screens.venuesoverview.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adyen.android.assignment.data.repositories.VenuesOverviewRepositoryImpl


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


class VenuesOverviewViewModelFactory(
    private val application: Application,
    private val venuesOverviewRepository: VenuesOverviewRepositoryImpl
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VenuesOverviewViewModel::class.java)){
            return VenuesOverviewViewModel(application, venuesOverviewRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}