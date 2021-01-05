package com.rocbillow.doraemon.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.rocbillow.base.extension.autoCleared
import com.rocbillow.base.extension.dataBinding
import com.rocbillow.base.extension.start
import com.rocbillow.common.base.BaseFragment
import com.rocbillow.doraemon.R
import com.rocbillow.doraemon.databinding.FragmentMainBinding
import com.rocbillow.uikit.recyclerview.GridDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author rocbillow
 * @date 2020-09-07
 */
@AndroidEntryPoint
class MainFragment : BaseFragment() {

  companion object {
    private const val SPAN_COUNT = 3
  }

  private val mainViewModel: MainViewModel by viewModels(this::requireActivity)
  private var binding by autoCleared<FragmentMainBinding>()
  private lateinit var multiTypeAdapter: MultiTypeAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    binding = dataBinding(inflater, R.layout.fragment_main, container)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    bindUi()
    subscribeUi()
  }

  private fun bindUi() {
    binding.apply {
      bindRecyclerView(recyclerView)
    }
  }

  private fun subscribeUi() {
    mainViewModel.module.observe(viewLifecycleOwner) {
      multiTypeAdapter.items = it
      multiTypeAdapter.notifyDataSetChanged()
    }
  }

  private fun bindRecyclerView(recyclerView: RecyclerView) {
    multiTypeAdapter = MultiTypeAdapter()
    multiTypeAdapter.register(ModuleItemViewBinder { start(it.clazz) })

    recyclerView.apply {
      addItemDecoration(GridDividerItemDecoration(context, SPAN_COUNT))
      layoutManager = GridLayoutManager(context, SPAN_COUNT)
      adapter = multiTypeAdapter
    }
  }
}
