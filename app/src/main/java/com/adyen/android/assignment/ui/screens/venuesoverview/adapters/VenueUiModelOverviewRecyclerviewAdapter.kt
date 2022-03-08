package com.adyen.android.assignment.ui.screens.venuesoverview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.databinding.ListItemVenueOverviewBinding
import com.adyen.android.assignment.ui.screens.venuesoverview.viewmodels.VenuesOverviewViewModel
import com.adyen.android.assignment.ui.uimodels.VenueUiModel


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


class VenueUiModelOverviewRecyclerviewAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<VenueUiModel, VenueUiModelOverviewRecyclerviewAdapter.ViewHolder>(VenueUiModelDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val venue = getItem(position)

        holder.bind(venue, onClickListener)
    }

    class ViewHolder private constructor(private val binding: ListItemVenueOverviewBinding) : RecyclerView.ViewHolder(binding.root){

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemVenueOverviewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

        fun bind(venueUiModel: VenueUiModel, clickListener: OnClickListener){
            binding.venueUiModel = venueUiModel
            binding.clickListener = clickListener
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object VenueUiModelDiffCallback : DiffUtil.ItemCallback<VenueUiModel>(){
        override fun areItemsTheSame(oldItem: VenueUiModel, newItem: VenueUiModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: VenueUiModel, newItem: VenueUiModel): Boolean {
            return oldItem.latitude == newItem.latitude && oldItem.longitude == newItem.longitude
        }
    }

    class OnClickListener(private val clickListener: (venue: VenueUiModel) -> Unit){

        fun onVenueClick(venue: VenueUiModel) = clickListener(venue)

    }

}