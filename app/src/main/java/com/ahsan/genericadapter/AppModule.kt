package com.ahsan.genericadapter

import com.ahsan.genericadapter.data.ItemDataSource
import com.ahsan.genericadapter.data.ItemRepository
import com.ahsan.genericadapter.data.MockFirstItemDataSource
import com.ahsan.genericadapter.domain.FetchItemsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val USE_CASE_GET_ITEMS = "USE_CASE_GET_ITEMS"
const val DATA_SOURCE_ITEMS_MOCK = "DATA_SOURCE_ITEMS_MOCK"
const val REPOSITORY_ITEMS = "REPOSITORY_ITEMS"

val appModule = module {

    viewModel {
        ItemViewModel(get(named(USE_CASE_GET_ITEMS)))
    }

    factory(named(USE_CASE_GET_ITEMS)) {
        FetchItemsUseCase(get(named(REPOSITORY_ITEMS)))
    }

    factory(named(REPOSITORY_ITEMS)) {
        ItemRepository(get(named(DATA_SOURCE_ITEMS_MOCK)))
    }

    factory<ItemDataSource>(named(DATA_SOURCE_ITEMS_MOCK)) {
        MockFirstItemDataSource()
    }

}