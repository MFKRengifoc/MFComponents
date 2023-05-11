package com.manoffocus.mfcomponentsnav.ui.mfmaps

import android.Manifest
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.easywaylocation.draw_path.PolyLineDataBean
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.manoffocus.mfcomponentsnav.R
import com.manoffocus.mfcomponentsnav.components.mfmaps.MapsContract
import com.manoffocus.mfcomponentsnav.components.mfmaps.MapsPresenter
import com.manoffocus.mfcomponentsnav.components.mfmodalbottomsheet.MFBasePlainBottomSheetFragment
import com.manoffocus.mfcomponentsnav.components.mfmodalbottomsheet.MFModalBottomSheet
import com.manoffocus.mfcomponentsnav.databinding.FragmentMfMapsBinding
import com.manoffocus.mfcomponentsnav.utils.MFLocationManager

class MFMapsFragment : Fragment(),
    MapsContract.View, MapsContract.Maps {
    private var _binding: FragmentMfMapsBinding? = null
    private var locationPresenter: MapsContract.LocationPresenter? = null
    private var mapsPresenter: MapsContract.Presenter? = null
    private val locationPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permission ->
        when {
            permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.d("PERMISSION", "Permiso concedido ${locationPresenter}")
                locationPresenter?.locationPermissionGranted()
            }
            permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.d("PERMISSION", "Permiso concedido pero limitado ${locationPresenter}")
                locationPresenter?.locationPermissionGranted()
            } else -> {
                Log.d("PERMISSION", "Permiso denegado")
                locationPresenter?.locationPermissionRefused()
            }
        }
    }
    private val gpsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (locationPresenter?.isGpsEnabled(requireContext()) == true) {
            locationPresenter?.locationPermissionGranted()
        }
    }


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMfMapsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        locationPresenter = MFLocationManager(this)
        mapsPresenter = MapsPresenter(this)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestLocationPermissions() {
        locationPermission.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
    private fun requestGps() {
        val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        gpsLauncher.launch(i)
    }

    private fun getLocationModal(): MFModalBottomSheet {
        val model = MFBasePlainBottomSheetFragment.newInstance(R.layout.mf_request_location_fragment)
        val listener = object : MFModalBottomSheet.OnDismissModalListener{
            override fun onAccept() {
                requestLocationPermissions()
            }
            override fun onCancel() { Log.d(TAG, "onCancel: ") }
            override fun onClose() { Log.d(TAG, "onMinize: ") }
        }
        val modal = MFModalBottomSheet.newInstance(model, timed = false, showCancelButton = false, isClosable = false, listener = listener)
        return modal
    }
    private fun getGpsModal(): MFModalBottomSheet {
        val model = MFBasePlainBottomSheetFragment.newInstance(R.layout.mf_request_gps_activate_fragment)
        val listener = object : MFModalBottomSheet.OnDismissModalListener{
            override fun onAccept() {
                requestGps()
            }
            override fun onCancel() { Log.d(TAG, "onCancel: ") }
            override fun onClose() { Log.d(TAG, "onMinize: ") }
        }
        val modal = MFModalBottomSheet.newInstance(model, timed = false, showCancelButton = false, isClosable = false, listener = listener)
        return modal
    }

    override val fragmentMan: FragmentManager
        get() = childFragmentManager
    override val viewActivity: FragmentActivity
        get() = requireActivity()

    override fun setPresenter(pr: MapsContract.LocationPresenter) {
        locationPresenter = pr
        locationPresenter?.initLocation()
    }
    override fun showLocationPermissionsRequest() {
        val modal = getLocationModal()
        modal.show(parentFragmentManager, MFModalBottomSheet.TAG)
    }

    override fun showGpsRequest() {
        val modal = getGpsModal()
        modal.show(parentFragmentManager, MFModalBottomSheet.TAG)
    }

    override fun onCurrentLocation(location: Location) {
        mapsPresenter?.setCameraByLocation(LatLng(location.latitude, location.longitude))
    }

    override fun onFragmentMapReady(googleMap: GoogleMap) {
    }
    override fun onPathFinished(
        polyLineDetailsMap: HashMap<String, PolyLineDataBean>,
        polyLineDetailsArray: ArrayList<PolyLineDataBean>
    ) {
    }
    override fun onRefusedLocation() {
    }

    override fun onAcceptedLocation() {
        locationPresenter?.startLocation()
    }

    companion object {
        private const val TAG = "MFMapsFragment"
    }
}