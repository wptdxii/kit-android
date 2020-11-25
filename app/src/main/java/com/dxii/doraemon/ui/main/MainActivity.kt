package com.dxii.doraemon.ui.main

import android.os.Bundle
import androidx.fragment.app.commitNow
import androidx.navigation.fragment.NavHostFragment
import com.dxii.basekit.base.BaseActivity
import com.dxii.basekit.ext.binding
import com.dxii.doraemon.R
import com.dxii.doraemon.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author idxii
 * @date 2020-08-07
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "com.idxii.doraemon.ui.main.MainActivity"
    }

    private val binding: ActivityMainBinding by binding(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            addNavHostFragment()
        }
    }

    private fun addNavHostFragment() {
        val existingFragment = supportFragmentManager.findFragmentByTag(TAG)
        if (existingFragment == null) {
            val navHostFragment = NavHostFragment.create(R.navigation.nav_graph)
            supportFragmentManager.commitNow {
                setPrimaryNavigationFragment(navHostFragment)
                add(R.id.navHostFragmentContainer, navHostFragment, TAG)
            }
        }
    }
}