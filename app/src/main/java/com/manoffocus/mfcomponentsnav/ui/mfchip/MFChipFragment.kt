package com.manoffocus.mfcomponentsnav.ui.mfchip

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.manoffocus.mfcomponentsnav.R
import com.manoffocus.mfcomponentsnav.components.mfchip.MFChip
import com.manoffocus.mfcomponentsnav.components.mfchip.MFChipModel
import com.manoffocus.mfcomponentsnav.databinding.FragmentMfChipBinding

class MFChipFragment : Fragment() {

    private var _binding: FragmentMfChipBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mfChipViewModel = ViewModelProvider(this).get(MFChipViewModel::class.java)
        _binding = FragmentMfChipBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mfChipViewModel.mfChips.observe(viewLifecycleOwner) {
            drawChips(it)
        }
        return root
    }
    private fun drawChips(chips: List<MFChipModel>){
        for (chip in chips){
            val mfchipview = MFChip(ContextThemeWrapper(requireContext(), R.style.MFChip_small))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1F
            )
            mfchipview.layoutParams = lp
            mfchipview.externalSetChipTitleText(chip.title_text)
            mfchipview.externalSetChipValueText(chip.value_text)
            chip.icon?.let {
                mfchipview.externalSetStartIcon(MFChipModel.getDrawableFromResInt(requireContext(), it))
            }
            binding.activityMfchipBox.addView(mfchipview)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}