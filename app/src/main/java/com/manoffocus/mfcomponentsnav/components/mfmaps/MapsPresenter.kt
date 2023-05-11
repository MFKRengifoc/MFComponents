    package com.manoffocus.mfcomponentsnav.components.mfmaps

    import android.content.res.Resources
    import android.util.Log
    import com.example.easywaylocation.draw_path.PolyLineDataBean
    import com.google.android.gms.maps.GoogleMap
    import com.google.android.gms.maps.model.*
    import com.manoffocus.mfcomponentsnav.R
    import com.manoffocus.mfcomponentsnav.components.mfmaps.MapsContract.Presenter


    class MapsPresenter internal constructor(var mView: MapsContract.Maps, resId: Int = R.id.map) :
        Presenter {
        private var mapsFragment : MFMaps? = null
        init {
            try {
                mapsFragment = mView.fragmentMan.findFragmentById(resId) as MFMaps
                mapsFragment?.setPresenter(this)
            } catch (e: Exception) {
                Log.d(TAG, "ERROR: ${e.message}")
            }
        }

        override fun addMarkerToMap(coors: LatLng, icon: Int, label: String) : Marker {
            return mapsFragment!!.addMarkerToMap(coors, icon, label)
        }

        override fun drawRoute(origin: LatLng, destination: LatLng) {
            mapsFragment?.drawRoute(origin, destination)
        }

        override fun drawTripRoute(
            origin: LatLng,
            destination: LatLng,
            originTitle: String,
            destinationTitle: String
        ){
            mapsFragment?.let { maps ->
                maps.drawTripRoute(origin, destination, originTitle, destinationTitle)
            }
        }

        override fun onFragmentMapReady(googleMap: GoogleMap) {
            mView.onFragmentMapReady(googleMap = googleMap)
        }

        override fun onPathFinished(
            polyLineDetailsMap: HashMap<String, PolyLineDataBean>,
            polyLineDetailsArray: ArrayList<PolyLineDataBean>
        ) {
            mView.onPathFinished(polyLineDetailsMap, polyLineDetailsArray)
        }

        override fun setCameraByLocation(latLng: LatLng) {
            mapsFragment?.let { maps ->
                maps.setCameraByLocation(latLng)
            }
        }

        override fun centerMapByPoints(origin: LatLng, destination: LatLng) {
            mapsFragment?.centerMapByPoints(origin, destination)
        }
        override fun clearMap() {
            mapsFragment?.clearMap()
        }

        override fun setMapStyle(style: Int, googleMap: GoogleMap) {
            try {
                val success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(mView.viewActivity, style)
                )
            } catch (e: Resources.NotFoundException) {
                Log.d("MAPAS","Error ${e}")
            }
        }
        companion object {
            const val TAG = "MapsPresenter"
        }
    }