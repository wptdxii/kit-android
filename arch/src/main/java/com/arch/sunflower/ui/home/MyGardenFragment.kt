package com.arch.sunflower.ui.home

import android.os.Bundle
import android.view.View
import com.arch.sunflower.R
import com.arch.sunflower.databinding.FragmentMyGardenBinding
import com.olaroc.core.base.BaseFragment
import com.olaroc.core.binding.dataBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyGardenFragment @Inject constructor() : BaseFragment(R.layout.fragment_my_garden) {

    private val dataBinding by dataBinding { FragmentMyGardenBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUi()
    }

    private fun bindUi() {

    }
}