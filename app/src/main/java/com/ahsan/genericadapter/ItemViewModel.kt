package com.ahsan.genericadapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahsan.genericadapter.domain.FetchItemsUseCase
import com.ahsan.genericadapter.util.CofeStateFlow
import kotlinx.coroutines.launch

class ItemViewModel(private val fetchItemsUseCase: FetchItemsUseCase) : ViewModel() {
    val itemsStateFlow = CofeStateFlow(ItemUiState())

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            val result = fetchItemsUseCase.execute()
            itemsStateFlow.updateState {
                copy(items = result)
            }
        }
    }
}

data class ItemUiState(
    val isLoading: Boolean = false,
    val items: List<AdapterItem> = emptyList()
)