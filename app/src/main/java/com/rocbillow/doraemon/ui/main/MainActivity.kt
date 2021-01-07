package com.rocbillow.doraemon.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.commitNow
import androidx.navigation.fragment.NavHostFragment
import com.rocbillow.base.extension.dataBinding
import com.rocbillow.base.extension.start
import com.rocbillow.common.base.BaseActivity
import com.rocbillow.doraemon.R
import com.rocbillow.doraemon.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author rocbillow
 * @date 2020-08-07
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

  companion object {
    private const val TAG = "com.rocbillow.doraemon.ui.main.MainActivity"

    @JvmStatic
    fun start(context: Context) {
      context.start(MainActivity::class.java)
    }
  }

  private val binding: ActivityMainBinding by dataBinding(R.layout.activity_main)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.executePendingBindings()
    addNavHostFragment()
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
