package com.rocbillow.component.sample.databinding

import androidx.lifecycle.viewModelScope
import com.rocbillow.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BasicDataBindingViewModel @Inject constructor() : BaseViewModel() {

    private val _name = MutableStateFlow("Ada")
    private val _lastname = MutableStateFlow("Lovelace")
    private val _likes = MutableStateFlow(0)

    val name = _name.asStateFlow()
    val lastname = _lastname.asStateFlow()
    val likes = _likes.asStateFlow()

    val popularity = _likes.map {
        when {
            it > 9 -> Popularity.STAR
            it > 4 -> Popularity.POPULAR
            else -> Popularity.NORMAL
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Popularity.NORMAL)

    fun onLike() {
        if (_likes.value < 10) {
            _likes.value++
        }
    }

    fun onUnlike() {
        if (_likes.value > 0) {
            _likes.value--
        }
    }
}

enum class Popularity {
    NORMAL,
    POPULAR,
    STAR
}