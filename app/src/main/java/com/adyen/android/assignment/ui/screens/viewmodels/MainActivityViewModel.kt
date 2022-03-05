package com.adyen.android.assignment.ui.screens.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 04th March, 2022.
 */


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext = getApplication<Application>()

    fun showComingSoonMessageToast(@StringRes message: Int){
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

}