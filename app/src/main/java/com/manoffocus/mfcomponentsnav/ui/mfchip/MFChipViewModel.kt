package com.manoffocus.mfcomponentsnav.ui.mfchip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manoffocus.mfcomponentsnav.R
import com.manoffocus.mfcomponentsnav.components.mfchip.MFChipModel

class MFChipViewModel : ViewModel() {
    private val _mfChips = MutableLiveData<List<MFChipModel>>().apply {
        value = loadChips()
    }
    val mfChips : LiveData<List<MFChipModel>> = _mfChips
    fun loadChips(): List<MFChipModel> {
        return listOf(
            MFChipModel(
                title_text = "Titulo",
                value_text = "Contenido",
                icon = R.drawable.ic_location
            ),
            MFChipModel(
                title_text = "Titulo 2",
                value_text = "Contenido 2",
                icon = R.drawable.ic_location
            ),
            MFChipModel(
                title_text = "Titulo 3",
                value_text = "Contenido 3",
                icon = R.drawable.ic_location
            ),
            MFChipModel(
                title_text = "Titulo 4",
                value_text = "Contenido 4",
                icon = R.drawable.ic_location
            ),
            MFChipModel(
                title_text = "Titulo 5",
                value_text = "Contenido 5",
                icon = R.drawable.ic_location
            ),
        )
    }
}