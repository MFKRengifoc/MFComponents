package com.manoffocus.mfcomponentsnav.components.mfmaps

import android.content.Context
import android.location.Location
import com.example.easywaylocation.draw_path.PolyLineDataBean
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker


interface MapsContract {

    interface Presenter : BasePresenter {
        fun addMarkerToMap(coors: LatLng, icon: Int, label: String) : Marker
        fun drawRoute(origin: LatLng, destination: LatLng)
        fun drawTripRoute(origin: LatLng, destination: LatLng, originTitle: String, destinationTitle: String)
        fun setMapStyle(style: Int, googleMap: GoogleMap)
        fun onFragmentMapReady(googleMap: GoogleMap)
        fun onPathFinished(polyLineDetailsMap: HashMap<String, PolyLineDataBean>, polyLineDetailsArray: ArrayList<PolyLineDataBean>)
        fun setCameraByLocation(latLng: LatLng)
        fun centerMapByPoints(origin: LatLng, destination: LatLng)
        fun clearMap()
    }
    interface View : BaseView<Presenter?> {
        fun setPresenter(pr : LocationPresenter)
        fun showLocationPermissionsRequest()
        fun showGpsRequest()
        fun onRefusedLocation()
        fun onAcceptedLocation()
        fun onCurrentLocation(location: Location)
    }
    interface Maps : BaseView<Presenter?> {
        fun onFragmentMapReady(googleMap: GoogleMap)
        fun onPathFinished(polyLineDetailsMap: HashMap<String, PolyLineDataBean>, polyLineDetailsArray: ArrayList<PolyLineDataBean>)
    }
    interface LocationPresenter : BasePresenter {
        fun initLocation()
        fun setLocationProvider()
        fun locationPermissionGranted()
        fun locationPermissionRefused()
        fun isLocationEnabled() : Boolean
        fun isGpsEnabled(ctx: Context): Boolean
        fun arePermissionsGranted(ctx: Context): Boolean
        fun endLocation()
        fun startLocation()
    }
}