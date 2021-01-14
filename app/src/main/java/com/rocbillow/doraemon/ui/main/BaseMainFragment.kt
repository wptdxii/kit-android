package com.rocbillow.doraemon.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.rocbillow.base.extension.autoCleared
import com.rocbillow.base.extension.dataBinding
import com.rocbillow.base.extension.start
import com.rocbillow.common.base.BaseFragment
import com.rocbillow.common.base.BaseViewModel
import com.rocbillow.doraemon.R
import com.rocbillow.doraemon.databinding.FragmentBaseMainBinding
import com.rocbillow.uikit.recyclerview.GridDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author rocbillow
 * @date 2020-09-07
 */
@AndroidEntryPoint
abstract class BaseMainFragment : BaseFragment() {

  companion object {
    private const val SPAN_COUNT = 3
  }

  protected var binding by autoCleared<FragmentBaseMainBinding>()
  private lateinit var multiTypeAdapter: MultiTypeAdapter
  private lateinit var baseMainViewModel: BaseMainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    multiTypeAdapter = MultiTypeAdapter().apply {
      register(ModuleItemViewBinder { start(it.clazz) })
    }
    baseMainViewModel = getMainViewModel().apply {
      module.value?.let {
        multiTypeAdapter.items = it
      }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View = dataBinding<FragmentBaseMainBinding>(inflater, R.layout.fragment_base_main, container)
    .also { binding = it }
    .root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    bindUi()
    subscribeUi()
  }

  protected open fun bindUi() {
    with(binding.recyclerView) {
      addItemDecoration(GridDividerItemDecoration(context, SPAN_COUNT))
      layoutManager = GridLayoutManager(context, SPAN_COUNT)
    }
  }

  abstract fun getMainViewModel(): BaseMainViewModel

  private fun subscribeUi() {
    baseMainViewModel.module.observe(viewLifecycleOwner) {
      val recyclerView = binding.recyclerView
      if (recyclerView.adapter == null) {
        recyclerView.adapter = multiTypeAdapter
      } else {
        multiTypeAdapter.notifyDataSetChanged()
      }
    }
  }
}

abstract class BaseMainViewModel : BaseViewModel() {

  private val _module = MutableLiveData<List<Module>>().apply {
    value = createModules()
  }

  //  val module: LiveData<List<Module>> by ::_module
  val module: LiveData<List<Module>> = _module

  abstract fun createModules(): List<Module>
}
