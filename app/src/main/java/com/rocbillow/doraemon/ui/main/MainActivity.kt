package com.rocbillow.doraemon.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.annotation.StringDef
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import com.rocbillow.base.extension.handler
import com.rocbillow.base.extension.start
import com.rocbillow.base.extension.toast
import com.rocbillow.base.extension.viewBinding
import com.rocbillow.base.generic.BaseActivity
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

  @Inject lateinit var archFragmentLazy: DaggerLazy<ArchFragment>
  @Inject lateinit var componentFragmentLazy: DaggerLazy<ComponentFragment>
  @Inject lateinit var dashboardFragmentLazy: DaggerLazy<DashboardFragment>

  private val viewModel: MainViewModel by viewModels()
  private val viewBinding: ActivityMainBinding by viewBinding { ActivityMainBinding.inflate(it) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    bindUi()
  }

  private fun bindUi() {
    bindToolbar()
    bindBottomNav()
    bindDrawer()
    selectFragment(viewModel.activeFragment)
  }

  private fun bindToolbar() {
    val drawerToggle = ActionBarDrawerToggle(this,
      viewBinding.drawerLayout,
      viewBinding.toolbar,
      R.string.action_open_drawer,
      R.string.action_close_drawer)

    val arrowDrawable = drawerToggle.drawerArrowDrawable
    arrowDrawable.color = ContextCompat.getColor(this, R.color.black)
    viewBinding.toolbar.navigationIcon = arrowDrawable

    viewBinding.drawerLayout.addDrawerListener(drawerToggle)
  }

  private fun bindDrawer() {
    with(viewBinding.navView) {
      setCheckedItem(getItemId(viewModel.activeFragment))
      setNavigationItemSelectedListener {
        viewBinding.drawerLayout.closeDrawers()
        if (it.groupId == R.id.group_navigation) {
          viewBinding.bottomNav.selectedItemId = it.itemId
        }
        true
      }
    }
  }

  private fun bindBottomNav() {
    with(viewBinding.bottomNav) {
      selectedItemId = getItemId(viewModel.activeFragment)
      viewBinding.toolbar.title = viewModel.activeFragment
      setOnNavigationItemSelectedListener { menuItem ->
        lateinit var tag: String
        when (menuItem.itemId) {
          R.id.archFragment -> tag = ArchFragment.TAG
          R.id.componentFragment -> tag = ComponentFragment.TAG
          R.id.dashboardFragment -> tag = DashboardFragment.TAG
        }
        selectFragment(tag)
        viewModel.activeFragment = tag
        viewBinding.toolbar.title = tag
        viewBinding.navView.setCheckedItem(getItemId(tag))
        true
      }
    }
  }

  @IdRes
  private fun getItemId(@FragmentTag tag: String): Int = when (tag) {
    ArchFragment.TAG -> R.id.archFragment
    ComponentFragment.TAG -> R.id.componentFragment
    DashboardFragment.TAG -> R.id.dashboardFragment
    else -> invalidateTag(tag)
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
        fragment = getFragment(tag)
        add(R.id.fragmentContainer, fragment, tag)
        setReorderingAllowed(true)
      } else {
        show(fragment)
        setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
      }
      setPrimaryNavigationFragment(fragment)
    }
  }

  private fun getFragment(@FragmentTag tag: String): Fragment = when (tag) {
    ArchFragment.TAG -> archFragmentLazy.get()
    ComponentFragment.TAG -> componentFragmentLazy.get()
    DashboardFragment.TAG -> dashboardFragmentLazy.get()
    else -> invalidateTag(tag)
  }

  private fun invalidateTag(tag: String): Nothing =
    throw IllegalArgumentException("Fragment with tag $tag not exists.")

  private var pressedOnce: Boolean = false

  override fun onBackPressed() {
    if (pressedOnce) {
      super.onBackPressed()
      return
    }

    pressedOnce = true
    "Press back again to exit!".toast()
    handler.postDelayed(2000) { pressedOnce = false }
  }

  companion object {
    @JvmStatic
    fun start(context: Context) {
      context.start<MainActivity>()
    }
  }
}