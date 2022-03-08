package com.adyen.android.assignment.ui.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.ui.screens.venuesoverview.adapters.VenueUiModelOverviewRecyclerviewAdapter
import com.adyen.android.assignment.ui.uimodels.VenueUiModel


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */



/**
 * When there is no cart item data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("bindVenues")
fun setVenues(recyclerView: RecyclerView, venues: List<VenueUiModel>?){
    if(venues == null || venues.isEmpty())
        recyclerView.visibility = View.GONE
    else
        recyclerView.visibility = View.VISIBLE

    val venuesAdapter = recyclerView.adapter as VenueUiModelOverviewRecyclerviewAdapter
    venuesAdapter.submitList(venues)
}