package com.olaroc.component.sample.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.olaroc.component.databinding.FragmentBasicDataBindingBinding
import com.olaroc.core.base.BaseFragment
import com.olaroc.core.binding.dataBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BasicDataBindingFragment : BaseFragment() {

    companion object {
        const val TAG = "Basic Data Binding"
    }

    private var dataBinding by dataBinding<FragmentBasicDataBindingBinding>()
    private val viewModel by viewModels<BasicDataBindingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBasicDataBindingBinding.inflate(inflater, container, false)
        .also { dataBinding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUi()
        subscribeUi()
    }

    private fun bindUi() {
        dataBinding.viewModle = viewModel
        dataBinding.toolbar.title = TAG
    }

    private fun subscribeUi() {

    }
}