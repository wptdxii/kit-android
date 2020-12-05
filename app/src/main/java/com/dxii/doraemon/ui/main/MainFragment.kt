package com.dxii.doraemon.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.dxii.basekit.base.BaseFragment
import com.dxii.basekit.ext.autoCleared
import com.dxii.basekit.ext.binding
import com.dxii.basekit.ext.start
import com.dxii.doraemon.R
import com.dxii.doraemon.databinding.FragmentMainBinding
import com.dxii.uikit.recyclerview.GridDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author dxii
 * @date 2020-09-07
 */
@AndroidEntryPoint
class MainFragment : BaseFragment() {

    companion object {
        private const val SPAN_COUNT = 3
    }

    private val mainViewModel: MainViewModel by viewModels({ requireActivity() })

    private var binding by autoCleared<FragmentMainBinding>()
    private lateinit var multiTypeAdapter: MultiTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(inflater, R.layout.fragment_main, container)
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