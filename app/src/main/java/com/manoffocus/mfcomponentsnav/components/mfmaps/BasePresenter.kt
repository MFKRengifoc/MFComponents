package com.manoffocus.mfcomponentsnav.components.mfmaps
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


interface BasePresenter {
//    fun requestLocationPermissions()
//    fun requestGps()
}

interface BaseView<T> {
    val viewActivity: FragmentActivity
    val fragmentMan: FragmentManager
}