package com.manoffocus.mfcomponentsnav.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Open the menu to see the components"
    }
    val text: LiveData<String> = _text
}