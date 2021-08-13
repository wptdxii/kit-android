package com.arch.sunflower.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANTS_PAGE_INDEX = 1

class HomePagerAdapter(fragment: HomeViewPagerFragment) :
    FragmentStateAdapter(fragment) {


    private val tabFragmentsCreator: Map<Int, Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to MyGardenFragment(),
        PLANTS_PAGE_INDEX to PlantsFragment(),
    )

    override fun getItemCount(): Int = tabFragmentsCreator.size

    @Throws(IndexOutOfBoundsException::class)
    override fun createFragment(position: Int): Fragment =
        tabFragmentsCreator[position] ?: throw IndexOutOfBoundsException()
}