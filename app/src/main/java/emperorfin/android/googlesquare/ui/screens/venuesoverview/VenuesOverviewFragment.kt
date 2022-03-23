package emperorfin.android.googlesquare.ui.screens.venuesoverview

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import emperorfin.android.googlesquare.R
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.AppRoomDatabase
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.dao.VenueDao
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entitysources.VenueLocalDataSourceRoom
import emperorfin.android.googlesquare.data.datasources.remote.frameworks.retrofit.modelsources.VenueRemoteDataSourceRetrofit
import emperorfin.android.googlesquare.data.repositories.VenuesOverviewRepositoryImpl
import emperorfin.android.googlesquare.databinding.FragmentVenuesOverviewBinding
import emperorfin.android.googlesquare.ui.events.inputs.venue.None
import emperorfin.android.googlesquare.ui.events.inputs.venue.VenueParams
import emperorfin.android.googlesquare.ui.screens.venuesoverview.adapters.VenueUiModelOverviewRecyclerviewAdapter
import emperorfin.android.googlesquare.ui.screens.venuesoverview.viewmodels.VenuesOverviewViewModel
import emperorfin.android.googlesquare.ui.screens.venuesoverview.viewmodels.VenuesOverviewViewModelFactory
import emperorfin.android.googlesquare.ui.screens.utils.ErrorUtil
import com.google.android.material.snackbar.Snackbar
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class VenuesOverviewFragment : Fragment() {

    companion object {
        const val HTTP = "http"
    }

    private lateinit var mViewModel: VenuesOverviewViewModel

    private lateinit var venueLocalDataSource: VenueLocalDataSourceRoom
    private lateinit var venueRemoteDataSource: VenueRemoteDataSourceRetrofit
    private lateinit var venuesOverviewRepository: VenuesOverviewRepositoryImpl

    private lateinit var venueDao: VenueDao

    private var mSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_venues_overview, container, false)
        val binding =
            DataBindingUtil.inflate<FragmentVenuesOverviewBinding>(
                inflater, R.layout.fragment_venues_overview, container, false
            )

        val application = requireNotNull(this.activity).application

        venuesOverviewRepository = getVenuesOverviewRepository(application)

        val currentLocationArguments = VenuesOverviewFragmentArgs.fromBundle(requireArguments())

        val latitude: Double = currentLocationArguments.latitude.toDouble()
        val longitude: Double = currentLocationArguments.longitude.toDouble()

//        val paramsSomething: VenueParams = getVenueParamsSample()
        val paramsSomething = getVenueParams(latitude, longitude)
        val paramsNothing = None()

        mViewModel = getVenuesOverviewViewModel(application, venuesOverviewRepository)

        // Doesn't request for either remote or local venues since they would be available after
        // configuration changes (screen rotation in this case).
        if (mViewModel.isFirstRun)
            mViewModel.loadVenues(
                paramsNothing = paramsNothing,
                paramsSomething = paramsSomething
            )
        mViewModel.isFirstRun = false

        mViewModel.noInternetConnectionError.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null && it == VenuesOverviewViewModel.ERROR_CODE_NO_INTERNET_CONNECTION){
                mSnackBar = ErrorUtil.showError(
                    binding.rootLayout,
                    R.string.message_no_internet_connection,
                    R.string.message_try_again,
                    View.OnClickListener {
                        mViewModel.loadVenues(
                            paramsNothing = paramsNothing,
                            paramsSomething = paramsSomething
                        )
                })
            }

            mViewModel.emitNoInternetConnectionError(null)
        })

        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        val venueAdapterOnClickListener = VenueUiModelOverviewRecyclerviewAdapter.OnClickListener{
            val iconUrlNew: String = if(it.iconUrl.contains(HTTP)){
                it.iconUrl
            }else{
                "NO_ICON_URL"
            }

            showToastMessage(
                "Nearby location: \"${it.name.toUpperCase(Locale.ROOT)}\" \nIcon URL: $iconUrlNew"
            )
        }

        setupVenueAdapter(binding, venueAdapterOnClickListener)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        dismissSnackBar()
    }

    override fun onDetach() {
        super.onDetach()

        dismissSnackBar()
    }

    private fun getVenuesOverviewViewModel(
        application: Application, venuesOverviewRepository: VenuesOverviewRepositoryImpl
    ): VenuesOverviewViewModel{
        val viewModelFactory = VenuesOverviewViewModelFactory(application, venuesOverviewRepository)

        return ViewModelProvider(this, viewModelFactory).get(VenuesOverviewViewModel::class.java)
    }

    private fun setupVenueAdapter(
        binding: FragmentVenuesOverviewBinding,
        venueAdapterOnClickListener: VenueUiModelOverviewRecyclerviewAdapter.OnClickListener
    ){
        binding.mainLocationsRecycler.adapter =
            VenueUiModelOverviewRecyclerviewAdapter(venueAdapterOnClickListener)
    }

    private fun getVenuesOverviewRepository(application: Application): VenuesOverviewRepositoryImpl{
        venueDao = AppRoomDatabase.getInstance(application).mVenueDao

        venueLocalDataSource = VenueLocalDataSourceRoom(context = application, venueDao = venueDao)
        venueRemoteDataSource = VenueRemoteDataSourceRetrofit(context = application)

        return VenuesOverviewRepositoryImpl(
            venueLocalDataSource = venueLocalDataSource,
            venueRemoteDataSource = venueRemoteDataSource
        )
    }

    private fun getVenueParams(latitude: Double?, longitude: Double?): VenueParams{
        return VenueParams(
            latitude = latitude,
            longitude = longitude
        )
    }

    private fun getVenueParamsSample(): VenueParams{
//        val latitude: Double = 52.376510
        val latitude: Double = 6.1424097
//        val longitude: Double = 4.905890
        val longitude: Double = 6.7981638

        return VenueParams(
            latitude = latitude,
            longitude = longitude
        )
    }

    /**
     * Dismisses any SnackBar error message that is showing.
     */
    private fun dismissSnackBar() {
        mSnackBar?.let {
            it.dismiss()
            mSnackBar = null
        }
    }

    private fun showToastMessage(message: String) =
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
}