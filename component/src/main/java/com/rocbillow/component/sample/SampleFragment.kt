package com.rocbillow.component.sample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rocbillow.component.R
import com.rocbillow.component.databinding.FragmentSampleBinding
import com.rocbillow.core.base.BaseFragment
import com.rocbillow.core.binding.dataBinding
import com.rocbillow.core.uikit.recyclerview.addLinearDivider
import com.rocbillow.core.uikit.toast.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SampleFragment : BaseFragment(R.layout.fragment_sample) {

    private val dataBinding by dataBinding { FragmentSampleBinding.bind(it) }
    private val viewModel by viewModels<SampleViewModel>()
    private val sampleAdapter by lazy { SampleAdapter { it.name.toast() } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUi()
        subScribeUi()
    }

    private fun bindUi() {
        with(dataBinding.recyclerView) {
            adapter = sampleAdapter
            addLinearDivider()
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun subScribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect {
                    sampleAdapter.submitList(it)
                }
            }
        }
    }
}