package emperorfin.android.googlesquare.ui.screens.map

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import emperorfin.android.googlesquare.R
import emperorfin.android.googlesquare.databinding.FragmentMapBinding
import emperorfin.android.googlesquare.ui.screens.map.contracts.IMapFragmentCallbacks
import emperorfin.android.googlesquare.ui.screens.map.viewmodels.MapViewModel
import emperorfin.android.googlesquare.ui.screens.map.viewmodels.MapViewModelFactory
import emperorfin.android.googlesquare.ui.screens.utils.ErrorUtil
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import emperorfin.android.googlesquare.ui.utils.InternetConnectivityUtil.hasInternetConnection
import emperorfin.android.googlesquare.ui.utils.InternetConnectivityTypeUtil.getConnectivityType
import emperorfin.android.googlesquare.ui.utils.InternetConnectivityTypeUtil.ACTION_CONNECTIVITY_CHANGE
import emperorfin.android.googlesquare.ui.utils.InternetConnectivityTypeUtil.TYPE_NOT_CONNECTED

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    private lateinit var mViewModel: MapViewModel

    private var mGoogleMap: GoogleMap? = null

    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationCallback: LocationCallback
    private var mCurrentLocation: Location? = null
    private val locationProviderClient: FusedLocationProviderClient by lazy {
        FusedLocationProviderClient(requireActivity())
    }

    private var mSnackBar: Snackbar? = null

    private var isLocationPermissionProcessStarted: Boolean = false

    private var mLocationPermissionDialog: AlertDialog? = null

    private lateinit var mIMapFragmentCallbacks: IMapFragmentCallbacks

    private var internetConnectivityChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val internetConnectivityType: Int = getConnectivityType(context!!)

            if (ACTION_CONNECTIVITY_CHANGE == intent?.action) {
                if (internetConnectivityType == TYPE_NOT_CONNECTED) {
                    showToastMessage(getString(R.string.toast_internet_off))
                } else {
                    showToastMessage(getString(R.string.toast_internet_on))

                    mSnackBar?.let {
                        it.dismiss()
                        mSnackBar = null

                        showToastMessage(getString(R.string.toast_automatic_refresh))

                        requestLocation()
                    }
                }
            }
        }
    }

    companion object {

        const val GOOGLE_MAPS_ZOOM = 13f

        // San Francisco Coordinates.
        private const val GOOGLE_MAPS_LATITUDE = 37.7610237
        private const val GOOGLE_MAPS_LONGITUDE = -122.4217785
        private const val GOOGLE_MAPS_LATITUDE_NEW_ZOOM = 37.76496792
        private const val GOOGLE_MAPS_LONGITUDE_NEW_ZOOM = -122.42206407

        private const val GOOGLE_MAPS_URI = "http://maps.google.com/maps"
        private const val URI_GOOGLE_MAPS_WITH_DESTINATION_LAT_LONG =
            "${GOOGLE_MAPS_URI}?daddr=${GOOGLE_MAPS_LATITUDE},${GOOGLE_MAPS_LONGITUDE}"

        private const val UPDATE_INTERNAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERNAL_IN_MILLISECONDS: Long = 2000

        private const val PERMISSION_LOCATION_REQUEST_CODE = 579

        private const val PACKAGE = "package"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mIMapFragmentCallbacks = requireActivity() as IMapFragmentCallbacks

//        requestLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application

        mViewModel = getMapViewModel(application)

        // Do not request for location coordinates since it would be available after configuration
        // changes.
        if (mViewModel.isFirstRun)
            requestLocation()
        mViewModel.isFirstRun = false

        binding = DataBindingUtil.inflate<FragmentMapBinding>(
            inflater, R.layout.fragment_map, container, false
        )

        mViewModel.currentLocationLiveData.observe(viewLifecycleOwner, Observer {
            // Stop disable progress bar.
            dismissSnackBar()

            if (it != null){
                mIMapFragmentCallbacks.onCurrentLocationReady(it)

                val latitudeNewZoom = it.latitude + (-0.00394422)
                val longitudeNewZoom = it.longitude + 0.00028557

                initMapFragment(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    latitudeNewZoom = latitudeNewZoom,
                    longitudeNewZoom = longitudeNewZoom
                )

                binding.map.visibility = View.VISIBLE

                if (!hasInternetConnection(application)){
                    mViewModel.emitNoInternetConnectionError(
                        MapViewModel.ERROR_CODE_NO_INTERNET_CONNECTION
                    )
                }

            }else{
                showToastMessage(getString(R.string.toast_app_closing))

                requireActivity().finish()
            }

        })

        mViewModel.noInternetConnectionError.observe(viewLifecycleOwner, Observer {
            if (it != null && it == MapViewModel.ERROR_CODE_NO_INTERNET_CONNECTION){
                mSnackBar = ErrorUtil.showError(
                    binding.map,
                    R.string.message_no_internet_connection,
                    R.string.message_refresh,
                    View.OnClickListener {
                        requestLocation()
                    })
            }

            mViewModel.emitNoInternetConnectionError(null)
        })

        mViewModel.currentLocationResultsError.observe(viewLifecycleOwner, Observer {
            if (it != null && it == MapViewModel.ERROR_CODE_NO_CURRENT_LOCATION) {

                mSnackBar = ErrorUtil.showError(
                    binding.map,
                    R.string.message_location_not_saved,
                    R.string.launch,
                    View.OnClickListener {
                        val intent = Intent(
                            Intent.ACTION_VIEW, Uri.parse(URI_GOOGLE_MAPS_WITH_DESTINATION_LAT_LONG)
                        )
                        intent.resolveActivity(requireActivity().packageManager)?.let {
                            requireActivity().startActivity(intent)
                        }
                        requireActivity().finish()
                    })
            }

            mViewModel.emitCurrentLocationResultsError(null)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSystemBarColor(requireActivity(), R.color.colorPrimary)
    }

    override fun onStart() {
        super.onStart()

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

        requireActivity().registerReceiver(internetConnectivityChangeReceiver, intentFilter)
    }

    override fun onStop() {
        requireActivity().unregisterReceiver(internetConnectivityChangeReceiver)

        super.onStop()
    }

    override fun onResume() {
        super.onResume()

        if (isLocationPermissionProcessStarted && isPermissionNotGranted()) // May use !isPermissionGranted()
            showEnableLocationDialog()

        if (isLocationPermissionProcessStarted && isPermissionGranted()){
            requestLocation()

            isLocationPermissionProcessStarted = false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_LOCATION_REQUEST_CODE -> {

                isLocationPermissionProcessStarted = true

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    // Permission denied by user.
                    dismissSnackBar()

                    showEnableLocationDialog()
                } else {
                    // Permission granted.
                    isLocationPermissionProcessStarted = false
                    dismissSnackBar()

                    requestLocation()
                }
            }
        }
    }

    private fun getMapViewModel(application: Application): MapViewModel{
        val viewModelFactory = MapViewModelFactory(application)

        return ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)
    }

    private fun requestLocation(){
        mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = UPDATE_INTERNAL_IN_MILLISECONDS
        mLocationRequest.fastestInterval = FASTEST_UPDATE_INTERNAL_IN_MILLISECONDS
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest: LocationSettingsRequest = builder.build()

        val settingsClient: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mLocationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                mCurrentLocation = locationResult.lastLocation
            }
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            /*
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            */

            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_LOCATION_REQUEST_CODE
            )

            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            mCurrentLocation = it

            mViewModel.refreshCurrentLocation(mCurrentLocation)
        }

/*
//        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        LocationServices.getFusedLocationProviderClient(requireActivity()).requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()!!)
*/

    }

    private fun showEnableLocationDialog(){
        if (mLocationPermissionDialog == null){
            mLocationPermissionDialog = AlertDialog.Builder(requireActivity())
                .setMessage(R.string.error_closing_no_location_permission)
                .setPositiveButton(R.string.close) { _, _ ->
                    isLocationPermissionProcessStarted = false

                    requireActivity().finish()
                }
                .setNegativeButton(R.string.enable) {_, _ ->
                    isLocationPermissionProcessStarted = true

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts(PACKAGE, requireActivity().packageName, null)
                    requireActivity().startActivity(intent)
                }
                .setCancelable(false)
                .create()
        }
        mLocationPermissionDialog?.show()
    }

    private fun initMapFragment(
        latitude: Double, longitude: Double, latitudeNewZoom: Double, longitudeNewZoom: Double
    ){
//        val supportMapFragment: SupportMapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        val supportMapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(object : OnMapReadyCallback{
            override fun onMapReady(googleMap: GoogleMap) {
                googleMap?.let {
                    // set map type
                    it.mapType = GoogleMap.MAP_TYPE_NORMAL
                    // Enable / Disable zooming controls
                    it.uiSettings.isZoomControlsEnabled = false
                    // Enable / Disable Compass icon
                    it.uiSettings.isCompassEnabled = true
                    // Enable / Disable Rotate gesture
                    it.uiSettings.isRotateGesturesEnabled = true
                    // Enable / Disable zooming functionality
                    it.uiSettings.isZoomGesturesEnabled = true

                    it.uiSettings.isScrollGesturesEnabled = true
                    it.uiSettings.isMapToolbarEnabled = true
                }

                mGoogleMap = googleMap

                val markerOptions: MarkerOptions =
                    MarkerOptions().position(LatLng(latitude, longitude))

                mGoogleMap?.let { map ->
                    map.addMarker(markerOptions)
                    map.moveCamera(zoomingLocation(latitudeNewZoom, longitudeNewZoom))
                    map.setOnMarkerClickListener {
                        try {
                            map.animateCamera(zoomingLocation(latitudeNewZoom, longitudeNewZoom));
                        } catch (e: Exception) {
                        }
                        return@setOnMarkerClickListener true;
                    }
                }
            }
        })
    }

    private fun setSystemBarColor(activity: Activity, @ColorRes color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window;
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.statusBarColor = activity.resources.getColor(color);
        }
    }

    private fun zoomingLocation(latitudeNewZoom: Double, longitudeNewZoom: Double): CameraUpdate {
        return CameraUpdateFactory.newLatLngZoom(
            LatLng(latitudeNewZoom, longitudeNewZoom), GOOGLE_MAPS_ZOOM
        )
    }

    private fun getLocationAndHandlePermission(){
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_LOCATION_REQUEST_CODE)

            return
        }

        locationProviderClient.lastLocation.addOnSuccessListener {
            mCurrentLocation = it

            mViewModel.refreshCurrentLocation(mCurrentLocation)
        }
    }

    private fun isPermissionGranted(): Boolean {
        if ((ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            return true

        return false
    }

    private fun isPermissionNotGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
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