package com.ahsan.genericadapter.data

import com.ahsan.genericadapter.FirstItem
import kotlinx.coroutines.delay

class MockFirstItemDataSource : ItemDataSource {
    override suspend fun fetchItems(): List<FirstItem> {
        delay(1000)
        return List(50) { index ->
            FirstItem(id = index, name = "Item $index")
        }
    }
}