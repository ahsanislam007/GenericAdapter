package com.ahsan.genericadapter.domain

import com.ahsan.genericadapter.FirstItem
import com.ahsan.genericadapter.data.MockItemRepository

class FetchItemsUseCase(private val repository: MockItemRepository) {

    suspend fun execute(): List<FirstItem> {
        return repository.fetchItems()
    }
}