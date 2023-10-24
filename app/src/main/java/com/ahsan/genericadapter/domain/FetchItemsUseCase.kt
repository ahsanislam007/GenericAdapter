package com.ahsan.genericadapter.domain

import com.ahsan.genericadapter.FirstItem
import com.ahsan.genericadapter.data.ItemRepository

class FetchItemsUseCase(private val repository: ItemRepository) {

    suspend fun execute(): List<FirstItem> {
        return repository.fetchItems()
    }
}