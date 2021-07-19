package com.olaroc.doraemon.ui.main

import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author olaroc
 * @date 2021-01-07
 */
@AndroidEntryPoint
class ComposeFragment @Inject constructor() : BaseMainFragment() {

  private val viewModel by activityViewModels<ComposeViewModel>()

  companion object {
    const val TAG = "Compose"
  }

  override fun getMainViewModel(): ComposeViewModel = viewModel
}

@HiltViewModel
class ComposeViewModel @Inject constructor() : BaseMainViewModel() {

  override fun createModules(): List<Module> = emptyList()
}
