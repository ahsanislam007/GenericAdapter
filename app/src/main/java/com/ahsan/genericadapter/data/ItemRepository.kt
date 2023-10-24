package com.ahsan.genericadapter.data

import com.ahsan.genericadapter.FirstItem

class ItemRepository(private val dataSource: ItemDataSource) {
    suspend fun fetchItems(): List<FirstItem> {
        return dataSource.fetchItems()
    }
}