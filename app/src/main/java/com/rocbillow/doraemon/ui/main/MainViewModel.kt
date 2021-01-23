package com.rocbillow.doraemon.ui.main

import androidx.lifecycle.SavedStateHandle
import com.rocbillow.base.extension.SavedStateKeyValueDelegate
import com.rocbillow.base.generic.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author rocbillow
 * @date 2020-09-30
 */

private const val ACTIVE_FRAGMENT = "active_fragment"

@HiltViewModel
class MainViewModel @Inject constructor(handle: SavedStateHandle) : BaseViewModel() {

  @FragmentTag
  var activeFragment by SavedStateKeyValueDelegate(handle, ACTIVE_FRAGMENT, ArchFragment.TAG)
}
