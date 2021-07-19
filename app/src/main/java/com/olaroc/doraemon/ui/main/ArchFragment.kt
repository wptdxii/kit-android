package com.olaroc.doraemon.ui.main

import androidx.fragment.app.activityViewModels
import com.arch.sunflower.ui.GardenActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author olaroc
 * @date 2021-01-07
 */
@AndroidEntryPoint
class ArchFragment @Inject constructor() : BaseMainFragment() {

  companion object {
    const val TAG = "Arch"
  }

  private val viewModel by activityViewModels<ArchViewModel>()

  override fun getMainViewModel(): ArchViewModel = viewModel
}

@HiltViewModel
class ArchViewModel @Inject constructor() : BaseMainViewModel() {

  override fun createModules(): List<Module> = arrayListOf(
    Module("Sunflower", GardenActivity::class.java)
  )
}
