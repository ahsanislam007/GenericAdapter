package com.ahsan.genericadapter.data

import com.ahsan.genericadapter.FirstItem

class MockItemRepository(private val dataSource: ItemDataSource) {
    suspend fun fetchItems(): List<FirstItem> {
        return dataSource.fetchItems()
    }
}