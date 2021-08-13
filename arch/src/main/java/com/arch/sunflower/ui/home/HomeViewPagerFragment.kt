package com.arch.sunflower.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arch.sunflower.R
import com.arch.sunflower.databinding.FragmentHomeViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.olaroc.core.base.BaseFragment
import com.olaroc.core.binding.dataBinding
import com.olaroc.core.systembar.applyStatusBarInsetsToPadding
import com.olaroc.core.uikit.AppbarLayoutOffsetChangedListener
import com.olaroc.core.uikit.State
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeViewPagerFragment @Inject constructor() : BaseFragment() {

    private var dataBinding by dataBinding<FragmentHomeViewPagerBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeViewPagerBinding.inflate(layoutInflater, container, false)
        .also { dataBinding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUi()
    }

    override fun applySystemWindows() {
        super.applySystemWindows()
        dataBinding.appBarLayout.applyStatusBarInsetsToPadding()
    }

    private fun bindUi() {
        with(dataBinding) {
            appBarLayout.addOnOffsetChangedListener(AppbarLayoutOffsetChangedListener { _, state ->
                toolbar.title =
                    if (state != State.COLLAPSED) getString(R.string.sunflower_title) else ""
            })
            viewPager.adapter = HomePagerAdapter(this@HomeViewPagerFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.setIcon(getTabIcon(position))
                tab.text = getTabText(position)
            }.attach()
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    private fun getTabText(position: Int): CharSequence = when (position) {
        MY_GARDEN_PAGE_INDEX -> getString(R.string.my_garden_title)
        PLANTS_PAGE_INDEX -> getString(R.string.plants_title)
        else -> throw IndexOutOfBoundsException()
    }

    @Throws(IndexOutOfBoundsException::class)
    private fun getTabIcon(position: Int): Int = when (position) {
        MY_GARDEN_PAGE_INDEX -> R.drawable.selector_my_garden_tab
        PLANTS_PAGE_INDEX -> R.drawable.selector_plants_tab
        else -> throw IndexOutOfBoundsException()
    }
}