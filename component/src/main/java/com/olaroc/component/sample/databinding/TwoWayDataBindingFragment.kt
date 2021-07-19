package com.olaroc.component.sample.databinding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.olaroc.component.R
import com.olaroc.component.databinding.FragmentTwoWayDataBindingBinding
import com.olaroc.core.base.BaseFragment
import com.olaroc.core.binding.dataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TwoWayDataBindingFragment : BaseFragment(R.layout.fragment_two_way_data_binding) {

    companion object {
        const val TAG = "Two Way Data Binding"
    }

    private val viewModel by viewModels<TwoWayDataBindingViewModel>()
    private val dataBinding by dataBinding { FragmentTwoWayDataBindingBinding.bind(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.restorePrefs()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun bindUi() {
        dataBinding.viewModel = viewModel
        dataBinding.toolbar.title = TAG
    }
}