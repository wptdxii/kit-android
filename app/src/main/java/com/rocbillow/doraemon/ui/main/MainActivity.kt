package com.rocbillow.doraemon.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import com.rocbillow.base.extension.dataBinding
import com.rocbillow.base.extension.start
import com.rocbillow.common.base.BaseActivity
import com.rocbillow.doraemon.R
import com.rocbillow.doraemon.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import dagger.Lazy as DaggerLazy

/**
 * @author rocbillow
 * @date 2020-08-07
 */

@Retention(AnnotationRetention.SOURCE)
@StringDef(ArchFragment.TAG, DashboardFragment.TAG, ComponentFragment.TAG)
annotation class FragmentTag

@AndroidEntryPoint
class MainActivity : BaseActivity() {

  companion object {
    @JvmStatic
    fun start(context: Context) {
      context.start(MainActivity::class.java)
    }
  }

  @Inject
  lateinit var archFragmentLazy: DaggerLazy<ArchFragment>

  @Inject
  lateinit var componentFragmentLazy: DaggerLazy<ComponentFragment>

  @Inject
  lateinit var dashboardFragmentLazy: DaggerLazy<DashboardFragment>

  private val viewModel: MainViewModel by viewModels()

  private val binding: ActivityMainBinding by dataBinding(R.layout.activity_main)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    bindUi()
  }

  private fun bindUi() {
    with(binding.bottomNav) {
      selectedItemId = getItemIdByTag(viewModel.activeFragment)

      setOnNavigationItemSelectedListener { menuItem ->
        lateinit var tag: String
        when (menuItem.itemId) {
          R.id.archFragment -> tag = ArchFragment.TAG
          R.id.componentFragment -> tag = ComponentFragment.TAG
          R.id.dashboardFragment -> tag = DashboardFragment.TAG
        }
        selectFragment(tag)
        viewModel.activeFragment = tag
        true
      }
    }
    selectFragment(viewModel.activeFragment)
  }

  private fun getItemIdByTag(@FragmentTag tag: String): Int = when (tag) {
    ArchFragment.TAG -> R.id.archFragment
    ComponentFragment.TAG -> R.id.componentFragment
    DashboardFragment.TAG -> R.id.dashboardFragment
    else -> checkTag(tag)
  }

  private fun selectFragment(@FragmentTag tag: String) {
    supportFragmentManager.commitNow(true) {
      val primaryNavigationFragment = supportFragmentManager.primaryNavigationFragment
      primaryNavigationFragment?.let {
        if (it.tag != tag) {
          hide(primaryNavigationFragment)
          setMaxLifecycle(primaryNavigationFragment, Lifecycle.State.STARTED)
        }
      }

      var fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
      if (fragment == null) {
        fragment = getFragmentByTag(tag)
        add(R.id.fragmentContainer, fragment, tag)
      } else {
        show(fragment)
        setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
      }
      setPrimaryNavigationFragment(fragment)
      setReorderingAllowed(true)
    }
  }

  private fun getFragmentByTag(@FragmentTag tag: String): Fragment = when (tag) {
    ArchFragment.TAG -> archFragmentLazy.get()
    ComponentFragment.TAG -> componentFragmentLazy.get()
    DashboardFragment.TAG -> dashboardFragmentLazy.get()
    else -> checkTag(tag)
  }

  private fun checkTag(tag: String): Nothing =
    throw IllegalArgumentException("Fragment with tag $tag not exists")
}