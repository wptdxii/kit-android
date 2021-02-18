package com.rocbillow.doraemon.ui.main

import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author rocbillow
 * @date 2021-01-07
 */
@AndroidEntryPoint
class ComponentFragment @Inject constructor() : BaseMainFragment() {

  private val viewModel by activityViewModels<ComponentViewModel>()

  companion object {
    const val TAG = "Component"
  }

  override fun getMainViewModel(): BaseMainViewModel = viewModel
}

@HiltViewModel
class ComponentViewModel @Inject constructor() : BaseMainViewModel() {

  override fun createModules(): List<Module> = emptyList()
}