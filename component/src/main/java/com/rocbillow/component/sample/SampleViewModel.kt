package com.rocbillow.component.sample

import androidx.lifecycle.viewModelScope
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
        val samples = arrayListOf(
            Sample(SampleFragment.TAG, SampleFragmentDirections.actionToBasicDataBinding()),
        )
        viewModelScope.launch {
            _items.value = samples
        }
    }
}