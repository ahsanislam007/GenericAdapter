package com.ahsan.genericadapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ahsan.genericadapter.FirstItem.Companion.FIRST_TYPE
import com.ahsan.genericadapter.FirstItemViewHolder.Companion.createFirstItemViewHolder
import com.ahsan.genericadapter.SecondItem.Companion.SECOND_TYPE
import com.ahsan.genericadapter.SecondItemViewHolder.Companion.createSecondItemViewHolder
import com.ahsan.genericadapter.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CofeGenericAdapter

    private val viewModel: ItemViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeView()
        setupObservers()


    }

    private fun initializeView() {
        adapter = CofeGenericAdapter(
            items = mutableListOf(),
            viewHolderProviders = mapOf(
                FIRST_TYPE to { parent: ViewGroup ->
                    createFirstItemViewHolder(parent)
                },
                SECOND_TYPE to { parent: ViewGroup ->
                    createSecondItemViewHolder(parent)
                }
            )
        )

        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.itemsStateFlow.collect { uiState ->
                adapter.update(uiState.items)
//                adapter.filter { it is FirstItem && it.name.contains("keyword", ignoreCase = true) }

            }
        }

    }
}

