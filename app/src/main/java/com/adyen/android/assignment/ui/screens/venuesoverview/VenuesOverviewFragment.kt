package com.adyen.android.assignment.ui.screens.venuesoverview

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.adyen.android.assignment.R
import com.adyen.android.assignment.databinding.FragmentVenuesOverviewBinding
import com.adyen.android.assignment.ui.screens.venuesoverview.adapters.VenueUiModelOverviewRecyclerviewAdapter
import com.adyen.android.assignment.ui.screens.venuesoverview.viewmodels.VenuesOverviewViewModel
import com.adyen.android.assignment.ui.screens.venuesoverview.viewmodels.VenuesOverviewViewModelFactory
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class VenuesOverviewFragment : Fragment() {

    private lateinit var mViewModel: VenuesOverviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_venues_overview, container, false)
        val binding = DataBindingUtil.inflate<FragmentVenuesOverviewBinding>(inflater, R.layout.fragment_venues_overview, container, false)

        val application = requireNotNull(this.activity).application

        mViewModel = getVenuesOverviewViewModel(application)

        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        val venueAdapterOnClickListener = VenueUiModelOverviewRecyclerviewAdapter.OnClickListener{
            Toast.makeText(
                context,
                "Nearby location: \"${it.name.toUpperCase(Locale.ROOT)}\" \nIcon URL: ${it.iconUrl}",
                Toast.LENGTH_SHORT
            ).show()
        }

        setupVenueAdapter(binding, venueAdapterOnClickListener)

        return binding.root
    }

    private fun getVenuesOverviewViewModel(application: Application): VenuesOverviewViewModel{
        val viewModelFactory = VenuesOverviewViewModelFactory(application)

        return ViewModelProvider(this, viewModelFactory).get(VenuesOverviewViewModel::class.java)
    }

    private fun setupVenueAdapter(binding: FragmentVenuesOverviewBinding, venueAdapterOnClickListener: VenueUiModelOverviewRecyclerviewAdapter.OnClickListener){
        binding.mainLocationsRecycler.adapter = VenueUiModelOverviewRecyclerviewAdapter(venueAdapterOnClickListener)
    }
}