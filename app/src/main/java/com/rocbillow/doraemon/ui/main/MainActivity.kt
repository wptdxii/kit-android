package com.rocbillow.doraemon.ui.main

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.annotation.StringDef
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import com.rocbillow.core.base.BaseActivity
import com.rocbillow.core.extension.start
import com.rocbillow.core.binding.viewBinding
import com.rocbillow.core.uikit.toast.toast
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
@StringDef(ArchFragment.TAG, ComposeFragment.TAG, ComponentFragment.TAG)
annotation class FragmentTag

@AndroidEntryPoint
class MainActivity : BaseActivity() {

  companion object {
    @JvmStatic
    fun start(context: Context) {
      context.start<MainActivity>()
    }
  }

  @Inject lateinit var archFragmentLazy: DaggerLazy<ArchFragment>
  @Inject lateinit var componentFragmentLazy: DaggerLazy<ComponentFragment>
  @Inject lateinit var composeFragmentLazy: DaggerLazy<ComposeFragment>

  private val viewModel: MainViewModel by viewModels()
  private val viewBinding: ActivityMainBinding by viewBinding { ActivityMainBinding.inflate(it) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    bindUi()
    selectFragment(viewModel.activeFragment)
  }

  private fun bindUi() {
    bindToolbarAndDrawerLayout()
    bindNavigationView()
    bindBottomNavigationView()
  }

  private fun bindToolbarAndDrawerLayout() {
    val toolbar = viewBinding.toolbar
    val drawerLayout = viewBinding.drawerLayout
    val drawerToggle = ActionBarDrawerToggle(
      this, drawerLayout, toolbar, R.string.action_open_drawer, R.string.action_close_drawer
    )
    val arrowDrawable = drawerToggle.drawerArrowDrawable.apply {
      color = ContextCompat.getColor(this@MainActivity, R.color.black)
    }

    toolbar.navigationIcon = arrowDrawable
    drawerLayout.addDrawerListener(drawerToggle)
  }

  private fun bindNavigationView() {
    with(viewBinding) {
      navView.setCheckedItem(getItemId(viewModel.activeFragment))
      navView.setNavigationItemSelectedListener { selectNavigationViewItem(it) }
    }
  }

  private fun ActivityMainBinding.selectNavigationViewItem(it: MenuItem): Boolean {
    drawerLayout.closeDrawers()
    if (it.groupId == R.id.group_navigation) {
      bottomNav.selectedItemId = it.itemId
    }
    return true
  }

  private fun bindBottomNavigationView() {
    with(viewBinding) {
      bottomNav.selectedItemId = getItemId(viewModel.activeFragment)
      toolbar.title = viewModel.activeFragment
      bottomNav.setOnNavigationItemSelectedListener { menuItem ->
        selectBottomNavigationViewItem(menuItem)
      }
    }
  }

  private fun ActivityMainBinding.selectBottomNavigationViewItem(menuItem: MenuItem): Boolean {
    val tag = getTagByMenuItem(menuItem)
    selectFragment(tag)
    viewModel.activeFragment = tag
    toolbar.title = tag
    navView.setCheckedItem(getItemId(tag))
    return true
  }

  @FragmentTag
  private fun getTagByMenuItem(menuItem: MenuItem) = when (menuItem.itemId) {
    R.id.componentFragment -> ComponentFragment.TAG
    R.id.dashboardFragment -> ComposeFragment.TAG
    else -> ArchFragment.TAG
  }

  @IdRes
  private fun getItemId(@FragmentTag tag: String): Int = when (tag) {
    ArchFragment.TAG -> R.id.archFragment
    ComponentFragment.TAG -> R.id.componentFragment
    ComposeFragment.TAG -> R.id.dashboardFragment
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
    ComposeFragment.TAG -> composeFragmentLazy.get()
    else -> invalidateTag(tag)
  }

  private fun invalidateTag(tag: String): Nothing =
    throw IllegalArgumentException("Fragment with tag $tag not exists.")

  override fun onBackPressed() = pressAgainToExit()

  private var isClickedOnce: Boolean = false

  private fun pressAgainToExit() {
    if (isClickedOnce) {
      super.onBackPressed()
      return
    }

    isClickedOnce = true
    R.string.toast_msg_app_exit.toast()
    viewBinding.root.postDelayed({ isClickedOnce = false }, 2000)
  }
}