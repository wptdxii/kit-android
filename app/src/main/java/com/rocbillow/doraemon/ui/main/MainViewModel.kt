package com.rocbillow.doraemon.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rocbillow.common.base.BaseViewModel
import com.rocbillow.doraemon.ui.sunflower.GardenActivity

/**
 * @author rocbillow
 * @date 2020-09-30
 */

class MainViewModel @ViewModelInject constructor() : BaseViewModel() {

  private val _module = MutableLiveData<List<Module>>().apply {
    value = arrayListOf(
      Module(GardenActivity.TAG, GardenActivity::class.java)
    )
  }

  val module: LiveData<List<Module>>
    get() = _module
}