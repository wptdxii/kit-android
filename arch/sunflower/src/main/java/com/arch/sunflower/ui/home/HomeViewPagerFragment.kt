package com.arch.sunflower.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arch.sunflower.databinding.FragmentHomeViewPagerBinding
import com.rocbillow.core.base.BaseFragment
import com.rocbillow.core.binding.dataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeViewPagerFragment : BaseFragment() {

    private var dataBinding by dataBinding<FragmentHomeViewPagerBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeViewPagerBinding.inflate(layoutInflater, container, false)
        .also { dataBinding = it }
        .root
}