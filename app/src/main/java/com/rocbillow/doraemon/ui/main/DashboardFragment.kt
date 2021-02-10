package com.rocbillow.doraemon.ui.main

import androidx.fragment.app.activityViewModels
import com.rocbillow.doraemon.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author rocbillow
 * @date 2021-01-07
 */
@AndroidEntryPoint
class DashboardFragment @Inject constructor() : BaseMainFragment() {

  private val viewModel by activityViewModels<DashboardViewModel>()

  companion object {
    const val TAG = "com.rocbillow.doraemon.ui.main.dashboardFragment"
  }

  override fun bindUi() {
    super.bindUi()
    viewBinding.toolbar.setTitle(R.string.tab_text_main_dashboard)
  }

  override fun getMainViewModel(): DashboardViewModel = viewModel
}

@HiltViewModel
class DashboardViewModel @Inject constructor() : BaseMainViewModel() {

  override fun createModules(): List<Module> = emptyList()
}
