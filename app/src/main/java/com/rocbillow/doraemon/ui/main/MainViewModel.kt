package com.rocbillow.doraemon.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.rocbillow.base.extension.SavedStateKeyValueDelegate
import com.rocbillow.common.base.BaseViewModel

/**
 * @author rocbillow
 * @date 2020-09-30
 */

private const val ACTIVE_FRAGMENT = "active_fragment"

class MainViewModel @ViewModelInject constructor(
  @Assisted private val handle: SavedStateHandle,
) : BaseViewModel() {

  @FragmentTag
  var activeFragment by SavedStateKeyValueDelegate(handle, ACTIVE_FRAGMENT, ArchFragment.TAG)
}
