package com.manoffocus.mfcomponentsnav.components.mfmaps

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.easywaylocation.draw_path.DirectionUtil
import com.example.easywaylocation.draw_path.PolyLineDataBean
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import com.manoffocus.mfcomponentsnav.R
import com.manoffocus.mfcomponentsnav.databinding.MfMapsFragmentBinding
import kotlin.math.log10

class MFMaps : Fragment(), DirectionUtil.DirectionCallBack, GoogleMap.OnMapClickListener {

    private var api_key : String? = null
    private var _binding : MfMapsFragmentBinding? = null
    private val binding get() = _binding!!
    private var gmap : GoogleMap? = null
    private var wayPoints : ArrayList<LatLng> = ArrayList()
    private var directionUtil : DirectionUtil? = null
    private var presenter: MapsContract.Presenter? = null
    private var markers: ArrayList<LatLng> = arrayListOf()

    private val callback = OnMapReadyCallback { googleMap ->
        gmap = googleMap
        gmap?.also { map -> presenter?.onFragmentMapReady(map) }
        gmap?.setOnMapClickListener(this)
        setMapStyle(R.raw.style_night)
        arguments?.let { args ->
            val pos = LatLng(args.getDouble(INIT_LAT, 0.0), args.getDouble(INIT_LONG, 0.0))
            gmap?.let { map ->
                setCameraByLocation(pos)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MfMapsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            api_key = getString(R.string.google_maps_key)
        } catch (e: Exception){
            Log.d(TAG, "onViewCreated: ${e.message}")
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setMapStyle(style: Int) {
        try {
            val success = gmap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), style)
            )
        } catch (e: Resources.NotFoundException) {
            Log.d("MAPAS", "Error ${e}")
        }
    }

    fun setCameraByLocation(latLng: LatLng, zoom: Float = 15F){
        gmap?.moveCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder().target(latLng).zoom(zoom).build()
            )
        )
    }

    fun drawTripRoute(
        origin: LatLng,
        destination: LatLng,
        originTitle: String,
        destinationTitle: String
    ) {
        clearMap()
        addMarkerToMap(origin, R.drawable.mf_maps_origin_icon, originTitle)
        addMarkerToMap(destination, R.drawable.mf_maps_destination_icon, destinationTitle)
        drawRoute(origin, destination)
    }
    fun clearMap(){
        wayPoints.clear()
        gmap?.clear()
    }
    fun addMarkerToMap(coors: LatLng, icon: Int = R.drawable.mf_maps_origin_icon, label: String = "") : Marker {
        return gmap!!.addMarker(
            MarkerOptions().position(coors).icon(
                BitmapDescriptorFactory.fromResource(
                    icon
                )
            ).title(label)) as Marker
    }
    fun drawRoute(origin: LatLng, destination: LatLng) {
        wayPoints.add(origin)
        wayPoints.add(destination)
        api_key?.let { key ->
            gmap?.let { googleMap ->
                directionUtil = DirectionUtil.Builder()
                    .setDirectionKey(key)
                    .setOrigin(origin)
                    .setWayPoints(wayPoints)
                    .setGoogleMap(googleMap)
                    .setPolyLinePrimaryColor(R.color.black)
                    .setPolyLineWidth(10)
                    .setPathAnimation(true)
                    .setCallback(this)
                    .setDestination(destination)
                    .build()
                directionUtil?.initPath()
            }
        }
    }

    fun setPresenter(presenter: MapsContract.Presenter){
        this.presenter = presenter
    }

    fun centerMapByPoints(origin: LatLng, destination: LatLng){
        val yCurrentLatLng = LatLng(origin.latitude, destination.longitude)
        val xCurrentLatLng = LatLng(destination.latitude, origin.longitude)
        val areaPoints : ArrayList<LatLng> = ArrayList()
        areaPoints.add(origin)
        areaPoints.add(yCurrentLatLng)
        areaPoints.add(destination)
        areaPoints.add(xCurrentLatLng)
        val area = SphericalUtil.computeArea(areaPoints) / 1000000

        val zoom : Float = area.let { dist ->
            var z = 15F
            if (area < 1) {
                z+= area.toFloat()
            } else {
                z-=1 + log10(area).toFloat()
            }
            z
        }
        val midPoint = LatLng((origin.latitude + destination.latitude)/2, (origin.longitude + destination.longitude)/2)
        setCameraByLocation(latLng = midPoint, zoom)
    }


    override fun onMapClick(point: LatLng) {
        markers.add(point)
        if (markers.size == 2){
            drawTripRoute(origin = markers[0], destination = point, originTitle = "", destinationTitle = "")
        } else if (markers.size == 1){
            addMarkerToMap(markers[0])
        } else {
            markers.clear()
            clearMap()
        }
    }

    override fun pathFindFinish(
        polyLineDetailsMap: HashMap<String, PolyLineDataBean>,
        polyLineDetailsArray: ArrayList<PolyLineDataBean>
    ) {
        directionUtil?.drawPath(WAYPOINTS_TAG)
        presenter?.onPathFinished(polyLineDetailsMap, polyLineDetailsArray)
    }

    companion object {
        const val TAG = "MapsFragment"
        const val INIT_LAT = "init_lat"
        const val INIT_LONG = "init_long"
        val WAYPOINTS_TAG = TAG.lowercase() + "_waypoints_tag"
        fun newInstance(latLng: LatLng): MFMaps {
            val bundle = Bundle()
            bundle.putDouble(INIT_LAT, latLng.latitude)
            bundle.putDouble(INIT_LONG, latLng.longitude)
            val fragment = MFMaps()
            fragment.arguments = bundle
            return fragment
        }
    }
}