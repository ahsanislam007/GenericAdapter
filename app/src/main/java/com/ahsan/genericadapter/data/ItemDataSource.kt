package com.ahsan.genericadapter.data

import com.ahsan.genericadapter.FirstItem


interface ItemDataSource {
    suspend fun fetchItems():List<FirstItem>
}