package com.rocbillow.component.sample

import androidx.lifecycle.viewModelScope
import com.rocbillow.core.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SampleViewModel : BaseViewModel() {

    private val _items: MutableStateFlow<List<Sample>> = MutableStateFlow(emptyList())
    val items = _items.asStateFlow()

    init {
        val samples = arrayListOf(Sample("Basic Data Binding"))
        viewModelScope.launch {
            _items.value = samples
        }
    }
}