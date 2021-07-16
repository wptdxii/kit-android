package com.rocbillow.component.sample

import androidx.lifecycle.viewModelScope
import com.rocbillow.component.sample.databinding.BasicDataBindingFragment
import com.rocbillow.component.sample.databinding.TwoWayDataBindingFragment
import com.rocbillow.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor() : BaseViewModel() {

    private val _items: MutableStateFlow<List<Sample>> = MutableStateFlow(emptyList())
    val items = _items.asStateFlow()

    init {
        viewModelScope.launch {
            _items.value = createSamples()
        }
    }

    private fun createSamples(): ArrayList<Sample> = arrayListOf(
        Sample(
            BasicDataBindingFragment.TAG,
            SampleFragmentDirections.actionToBasicDataBinding()
        ),
        Sample(
            TwoWayDataBindingFragment.TAG,
            SampleFragmentDirections.actionToTwoWayDataBinding()
        )
    )
}