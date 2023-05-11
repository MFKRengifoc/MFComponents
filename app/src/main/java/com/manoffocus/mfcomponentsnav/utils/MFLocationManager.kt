package com.manoffocus.mfcomponentsnav.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.manoffocus.mfcomponentsnav.components.mfmaps.MapsContract

class MFLocationManager(var myview: MapsContract.View): MapsContract.LocationPresenter, Listener {
    private var easyWayLocation : EasyWayLocation? = null
    init {
        try {
            setLocationProvider()
            myview.setPresenter(this)
        } catch (e: Exception) {
            Log.d(TAG, "ERROR: ${e.message}")
        }
    }
     override fun initLocation(){
        requestUbication()
    }
    private fun requestUbication(){
        if (!arePermissionsGranted(myview.viewActivity)){
            myview.showLocationPermissionsRequest()
        } else {
            if (!isGpsEnabled(myview.viewActivity)){
                myview.showGpsRequest()
            } else {
                locationPermissionGranted()
            }
        }
    }
    override fun setLocationProvider() {
        val locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            fastestInterval = 0
            interval = 0
            smallestDisplacement = 1f
        }
        easyWayLocation = EasyWayLocation(myview.viewActivity, locationRequest, false, false, this)
    }
    override fun locationPermissionGranted() {
        myview.onAcceptedLocation()
    }
    override fun locationPermissionRefused() {
        myview.onRefusedLocation()
    }
    override fun isLocationEnabled(): Boolean {return false}
    override fun isGpsEnabled(ctx: Context): Boolean {
        val mLocationManager : LocationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    override fun arePermissionsGranted(ctx: Context): Boolean {
        return (
                ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                )
    }
    override fun endLocation() {
        easyWayLocation?.endUpdates()
    }
    override fun startLocation() {
        if (!isGpsEnabled(myview.viewActivity)){
            requestUbication()
        } else {
            easyWayLocation?.startLocation()
        }
    }

    override fun currentLocation(location: Location?) {
        location?.let { loc ->
            myview.onCurrentLocation(location)
        }
    }
    override fun locationOn() {}
    override fun locationCancelled() {}
    companion object {
        const val TAG = "LocationManager"
    }
}