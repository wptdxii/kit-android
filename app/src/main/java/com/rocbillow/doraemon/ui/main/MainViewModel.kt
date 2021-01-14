package com.rocbillow.doraemon.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.rocbillow.common.base.BaseViewModel

/**
 * @author rocbillow
 * @date 2020-09-30
 */

private const val KEY_INDEX = "active_fragment"

class MainViewModel @ViewModelInject constructor(
  @Assisted private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

  @FragmentTag
  var activeFragment
    get() = savedStateHandle[KEY_INDEX] ?: ArchFragment.TAG
    set(value) {
      savedStateHandle[KEY_INDEX] = value
    }
}
