package com.olaroc.doraemon.ui.main

import androidx.fragment.app.activityViewModels
import com.olaroc.component.sample.SampleActivity
import com.olaroc.component.scan.ScanActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author olaroc
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

    override fun createModules(): List<Module> = listOf(
        Module("Sample", SampleActivity::class.java),
        Module("Scan", ScanActivity::class.java),
    )
}