package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class StateViewModel : ViewModel() {

    private val _viewModelState = MutableStateFlow(UiState())
    val viewModelState = _viewModelState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        _viewModelState.value
    )

    init {
        nextRandom()
    }

    fun nextRandom() {
        val newValue: Int = Random.nextInt(0, 100)
        viewModelScope.launch {
            updateCounter(newValue)
            updateIsEven()
        }
    }

    private fun updateCounter(newValue: Int) {
        _viewModelState.update {
            it.copy(loading = true, value = newValue)
        }
    }

    private suspend fun updateIsEven() {
        _viewModelState.update {
            it.copy(loading = false, isEven = it.value.isEven())
        }
    }

}

data class UiState(
    val value: Int = 0,
    val isEven: String = "",
    val loading: Boolean = false
)