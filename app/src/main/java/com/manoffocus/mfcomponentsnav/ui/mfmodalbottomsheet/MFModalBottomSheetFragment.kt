package com.manoffocus.mfcomponentsnav.ui.mfmodalbottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manoffocus.mfcomponentsnav.components.mfmodalbottomsheet.MFModalBottomSheet
import com.manoffocus.mfcomponentsnav.components.mfmodalbottomsheet.MFTripInfo
import com.manoffocus.mfcomponentsnav.components.mfmodalbottomsheet.MFTripInfoModel
import com.manoffocus.mfcomponentsnav.databinding.FragmentMfModalBottomSheetBinding

class MFModalBottomSheetFragment : Fragment() {

    private var _binding: FragmentMfModalBottomSheetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMfModalBottomSheetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.activityMfModalBut.setOnClickListener {
            val modal = getModal()
            modal.show(parentFragmentManager, MFModalBottomSheet.TAG)
        }
        binding.activityMfModalTimeBut.setOnClickListener {
            val modaltimed = getModalTimed()
            modaltimed.show(parentFragmentManager, MFModalBottomSheet.TAG)
        }
        binding.activityMfModalNoCancelBut.setOnClickListener {
            val modalNocancelButton = getModalNoCancelBut()
            modalNocancelButton.show(parentFragmentManager, MFModalBottomSheet.TAG)
        }
        binding.activityMfModalNoAcceptBut.setOnClickListener {
            val modalNoButtons = getModalNoAcceptBut()
            modalNoButtons.show(parentFragmentManager, MFModalBottomSheet.TAG)
        }

        return root
    }
    fun getModal(): MFModalBottomSheet{
        val model = MFTripInfoModel(
            originName = "Origen",
            destinationName = "Destino",
            distance = 15.0,
            time = 1.0,
            minMaxPrices = doubleArrayOf(1.0, 2.0)
        )
        val fragment = MFTripInfo.newInstance(model)
        val modal = MFModalBottomSheet.newInstance(fragment, timed = false, isClosable = false)
        return modal
    }

    fun getModalTimed(): MFModalBottomSheet{
        val model = MFTripInfoModel(
            originName = "Origen",
            destinationName = "Destino",
            distance = 15.0,
            time = 1.0,
            minMaxPrices = doubleArrayOf(1.0, 2.0)
        )
        val fragment = MFTripInfo.newInstance(model)
        val listener = object : MFModalBottomSheet.OnDismissModalListener{
            override fun onAccept() { Log.d(TAG, "onAccept: ") }
            override fun onCancel() { Log.d(TAG, "onCancel: ") }
            override fun onClose() { Log.d(TAG, "onMinize: ") }
        }
        val modaltimed = MFModalBottomSheet.newInstance(fragment, timed = true, showAcceptButton = true, showCancelButton = true, listener = listener)
        return modaltimed
    }

    fun getModalNoCancelBut(): MFModalBottomSheet{
        val model = MFTripInfoModel(
            originName = "Origen",
            destinationName = "Destino",
            distance = 15.0,
            time = 1.0,
            minMaxPrices = doubleArrayOf(1.0, 2.0)
        )
        val fragment = MFTripInfo.newInstance(model)
        val modalNocancelButton = MFModalBottomSheet.newInstance(fragment, timed = true, showAcceptButton = true, showCancelButton = false)
        return modalNocancelButton
    }

    fun getModalNoAcceptBut(): MFModalBottomSheet{
        val model = MFTripInfoModel(
            originName = "Origen",
            destinationName = "Destino",
            distance = 15.0,
            time = 1.0,
            minMaxPrices = doubleArrayOf(1.0, 2.0)
        )
        val fragment = MFTripInfo.newInstance(model)
        val listener = object : MFModalBottomSheet.OnDismissModalListener{
            override fun onAccept() { Log.d(TAG, "onAccept: ") }
            override fun onCancel() { Log.d(TAG, "onCancel: ") }
            override fun onClose() { Log.d(TAG, "onMinize: ") }
        }
        val modalNoButtons = MFModalBottomSheet.newInstance(fragment, true, showAcceptButton = false, showCancelButton = false, listener = listener)
        return modalNoButtons
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        const val TAG = "MFModalBottomSheetFragment"
    }
}