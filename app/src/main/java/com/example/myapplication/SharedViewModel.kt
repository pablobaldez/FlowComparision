package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlin.random.Random

class SharedViewModel : ViewModel() {

    private val uiState = SharedUiState(0)
    private val _viewModelState = MutableSharedFlow<SharedUiState>()
    val viewModelState = _viewModelState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun init() {
        if (uiState.firstLoad) {
            uiState.firstLoad = false
            nextRandom()
        } else {
            viewModelScope.launch {
                _viewModelState.emit(uiState)
            }
        }
    }

    fun nextRandom() {
        val newValue: Int = Random.nextInt(0, 100)
        viewModelScope.launch {
            updateCounter(newValue)
            updateIsEven()
        }
    }

    private suspend fun updateCounter(newValue: Int) {
        // update the ui counter
        uiState.loading = true
        uiState.value = newValue
        _viewModelState.emit(uiState)
    }

    private suspend fun updateIsEven() {
        uiState.isEven = uiState.value.isEven()
        uiState.loading = false
        _viewModelState.emit(uiState)
    }

}

data class SharedUiState(
    var value: Int = Random.nextInt(0, 10),
    var isEven: String = "",
    var loading: Boolean = false,
    var firstLoad: Boolean = true
)