package com.manoffocus.mfcomponentsnav.components.mfmodalbottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manoffocus.mfcomponentsnav.components.mfbuttontexticon.MFButtonTextIcon
import com.manoffocus.mfcomponentsnav.components.mflabel.MFLabel
import com.manoffocus.mfcomponentsnav.databinding.MfTripFinOptionsFragmentBinding


/**
 * A simple [Fragment] subclass.
 * Use the [MFTripFinOptions.newInstance] factory method to
 * create an instance of this fragment.
 */
class MFTripFinOptions : Fragment(), View.OnClickListener {
    private var _binding : MfTripFinOptionsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MfTripFinOptionsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calificationBut : MFButtonTextIcon = binding.mfTripFinOptionGotoCalificationBut
        calificationBut.setOnClickListener(this)
        val noCalificationBut : MFLabel = binding.mfTripFinOptionCalificateNextTimeBut
        noCalificationBut.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment MFTripFinOptions.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = MFTripFinOptions()
    }

    override fun onClick(view: View?) {
        when(view?.id){
        }
    }
}