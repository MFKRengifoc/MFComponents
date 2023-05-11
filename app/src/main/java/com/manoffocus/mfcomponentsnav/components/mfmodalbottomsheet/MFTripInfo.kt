package com.manoffocus.mfcomponentsnav.components.mfmodalbottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manoffocus.mfcomponentsnav.R
import com.manoffocus.mfcomponentsnav.databinding.MfTripInfoFragmentBinding

/**
 * A simple [Fragment] subclass.
 * Use the [MFTripInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class MFTripInfo : Fragment() {
    private var _binding : MfTripInfoFragmentBinding? = null
    private val binding get() = _binding!!

    private var model : MFTripInfoModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            model = MFTripInfoModel(
                originName = bundle.getString(ORIGIN_NAME_TAG).orEmpty(),
                destinationName = bundle.getString(DESTINATION_NAME_TAG).orEmpty(),
                distance = bundle.getDouble(DISTANCE_TAG, 0.0),
                time = bundle.getDouble(TIME_TAG, 0.0),
                minMaxPrices = bundle.getDoubleArray(MIN_MAX_PRICES_TAG)?: run { doubleArrayOf() },
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = MfTripInfoFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model?.let { m ->
            val formatDistance = String.format("%.2f", m.distance)
            val formatTime = String.format("%.2f", m.time)
            val formatMinPrice = String.format("%.2f", m.minMaxPrices[0])
            val formatMaxPrice = String.format("%.2f", m.minMaxPrices[1])
            binding.mfTripInfoOriginDataChip.externalSetChipTitleText(getString(R.string.mf_trip_info_booking_client_title_label))
            binding.mfTripInfoOriginDataChip.externalSetChipValueText(m.originName)
            binding.mfTripInfoDestinationDataChip.externalSetChipTitleText(getString(R.string.mf_trip_info_booking_client_origin_label))
            binding.mfTripInfoDestinationDataChip.externalSetChipValueText(m.destinationName)
            binding.mfTripInfoDistanceTimeDataChip.externalSetChipTitleText(getString(R.string.mf_trip_info_booking_client_distance_time_label))
            binding.mfTripInfoDistanceTimeDataChip.externalSetChipValueText("$formatDistance min - $formatTime min")
            binding.mfTripInfoPriceDataChip.externalSetChipTitleText(getString(R.string.mf_trip_info_booking_client_money_label))
            binding.mfTripInfoPriceDataChip.externalSetChipValueText("$formatMinPrice - $formatMaxPrice €")
        }
    }

    companion object {
        const val ORIGIN_NAME_TAG = "origin_name_tag"
        const val DESTINATION_NAME_TAG = "destination_tag"
        const val DISTANCE_TAG = "distance_tag"
        const val TIME_TAG = "time_tag"
        const val MIN_MAX_PRICES_TAG = "min_max_prices_tag"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param model Parameter 1.
         * @return A new instance of fragment TripInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(model: MFTripInfoModel) =
            MFTripInfo().apply {
                arguments = Bundle().apply {
                    putString(ORIGIN_NAME_TAG, model.originName)
                    putString(DESTINATION_NAME_TAG, model.destinationName)
                    putDouble(DISTANCE_TAG, model.distance)
                    putDouble(TIME_TAG, model.time)
                    putDoubleArray(MIN_MAX_PRICES_TAG, model.minMaxPrices)
                }
            }
    }
}