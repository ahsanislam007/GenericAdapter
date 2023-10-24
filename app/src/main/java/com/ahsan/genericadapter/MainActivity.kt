package com.ahsan.genericadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ahsan.genericadapter.data.ItemDataSource
import com.ahsan.genericadapter.data.MockFirstItemDataSource
import com.ahsan.genericadapter.data.MockItemRepository
import com.ahsan.genericadapter.databinding.ActivityMainBinding
import com.ahsan.genericadapter.databinding.ItemSampleViewBinding
import com.ahsan.genericadapter.domain.FetchItemsUseCase

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CofeGenericAdapter<BaseViewHolder<AdapterItem>>
    private lateinit var viewModel: ItemViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dataSource: ItemDataSource = MockFirstItemDataSource()
        val repository = MockItemRepository(dataSource)
        val fetchItemsUseCase = FetchItemsUseCase(repository)

        viewModel = ItemViewModel(fetchItemsUseCase)
        initializeView()
        setupObservers()


    }

    private fun initializeView() {
        adapter = CofeGenericAdapter(
            items = mutableListOf(),
            viewHolderProviders = mapOf(
                VIEW_TYPE_ONE to { parent: ViewGroup ->
                    FirstItemViewHolder(
                        ItemSampleViewBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false,
                        )
                    ) as BaseViewHolder<AdapterItem>
                },
                VIEW_TYPE_TWO to { parent: ViewGroup ->
                    SecondItemViewHolder(
                        ItemSampleViewBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false,
                        )
                    ) as BaseViewHolder<AdapterItem>
                }
            )
        )

        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.itemsStateFlow.collect { uiState ->
                adapter.update(uiState.items)
            }
        }

    }

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }
}

