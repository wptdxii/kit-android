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
class DashboardFragment @Inject constructor() : BaseMainFragment() {

  private val viewModel by activityViewModels<DashboardViewModel>()

  companion object {
    const val TAG = "com.rocbillow.doraemon.ui.main.dashboardFragment"
  }

  override fun bindUi() {
    super.bindUi()
    binding.toolbar.setTitle(R.string.tab_text_main_dashboard)
  }

  override fun getMainViewModel(): DashboardViewModel = viewModel
}

class DashboardViewModel @ViewModelInject constructor() : BaseMainViewModel() {

  override fun createModules(): List<Module> = emptyList()
}
