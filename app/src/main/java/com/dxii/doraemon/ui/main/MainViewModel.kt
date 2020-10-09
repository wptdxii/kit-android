package com.dxii.doraemon.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dxii.basekit.base.BaseViewModel
import com.dxii.doraemon.ui.sunflower.GardenActivity

/**
 * @author wangpt
 * @date 2020-09-30
 */

class MainViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _module = MutableLiveData<List<Module>>()

    val module: LiveData<List<Module>>
        get() = _module

    init {
        _module.value = arrayListOf(
            Module(GardenActivity.TAG, GardenActivity::class.java)
        )
    }
}