package com.rocbillow.doraemon.ui.main

import androidx.fragment.app.activityViewModels
import androidx.hilt.lifecycle.ViewModelInject
import com.rocbillow.doraemon.R
import com.rocbillow.doraemon.ui.arch.sunflower.GardenActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author rocbillow
 * @date 2021-01-07
 */
@AndroidEntryPoint
class ArchFragment @Inject constructor() : BaseMainFragment() {

  companion object {
    const val TAG = "com.rocbillow.doraemon.ui.main.archFragment"
  }

  private val viewModel by activityViewModels<ArchViewModel>()

  override fun bindUi() {
    super.bindUi()
    binding.toolbar.setTitle(R.string.tab_text_main_arch)
  }

  override fun getMainViewModel(): ArchViewModel = viewModel
}

class ArchViewModel @ViewModelInject constructor() : BaseMainViewModel() {

  override fun createModules(): List<Module> = arrayListOf(
    Module("Sunflower", GardenActivity::class.java)
  )
}