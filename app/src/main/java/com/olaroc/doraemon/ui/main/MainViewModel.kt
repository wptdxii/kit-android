package com.olaroc.doraemon.ui.main

import androidx.lifecycle.SavedStateHandle
import com.olaroc.core.base.BaseViewModel
import com.olaroc.core.extension.SavedStateKeyValueDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author olaroc
 * @date 2020-09-30
 */

private const val ACTIVE_FRAGMENT = "active_fragment"

@HiltViewModel
class MainViewModel @Inject constructor(handle: SavedStateHandle) : BaseViewModel() {

  @setparam:FragmentTag
  var activeFragment by SavedStateKeyValueDelegate(handle, ACTIVE_FRAGMENT, ArchFragment.TAG)
}
