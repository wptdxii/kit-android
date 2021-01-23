package com.rocbillow.doraemon.ui.main

import androidx.fragment.app.activityViewModels
import androidx.hilt.lifecycle.ViewModelInject
import com.rocbillow.doraemon.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author rocbillow
 * @date 2021-01-07
 */
@AndroidEntryPoint
class ComponentFragment @Inject constructor() : BaseMainFragment() {

  private val viewModel by activityViewModels<ComponentViewModel>()

  companion object {
    const val TAG = "com.rocbillow.doraemon.ui.main.component"
  }

  override fun bindUi() {
    super.bindUi()
    viewBinding.toolbar.setTitle(R.string.tab_text_main_component)
  }

  override fun getMainViewModel(): BaseMainViewModel = viewModel
}

class ComponentViewModel @ViewModelInject constructor() : BaseMainViewModel() {

  override fun createModules(): List<Module> = emptyList()
}