package com.ahsan.genericadapter.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

class CofeStateFlow<T>(initialState: T) : Flow<T> {
    private val _state = MutableStateFlow(initialState)

    val value: T
        get() = _state.value

    fun updateState(transform: T.() -> T) {
        _state.value = _state.value.transform()
    }

    override suspend fun collect(collector: FlowCollector<T>) {
        _state.collect(collector)
    }
}

