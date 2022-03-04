package com.adyen.android.assignment.ui.screens.map

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.adyen.android.assignment.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {

    private var mGoogleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMapFragment()

        setSystemBarColor(requireActivity(), R.color.colorPrimary)
    }

    private fun initMapFragment(){
//        val supportMapFragment: SupportMapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        val supportMapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
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

                val markerOptions: MarkerOptions = MarkerOptions().position(LatLng(37.7610237, -122.4217785))

                mGoogleMap?.let { map ->
                    map.addMarker(markerOptions)
                    map.moveCamera(zoomingLocation())
                    map.setOnMarkerClickListener {
                        try {
                            map.animateCamera(zoomingLocation());
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

    private fun zoomingLocation(): CameraUpdate {
        return CameraUpdateFactory.newLatLngZoom(LatLng(GOOGLE_MAPS_LATITUDE, GOOGLE_MAPS_LONGITUDE), GOOGLE_MAPS_ZOOM)
    }

    companion object {

        const val GOOGLE_MAPS_ZOOM = 13f
        const val GOOGLE_MAPS_LATITUDE = 37.76496792
        const val GOOGLE_MAPS_LONGITUDE = -122.42206407

    }
}