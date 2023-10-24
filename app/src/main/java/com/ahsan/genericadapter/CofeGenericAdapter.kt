package com.ahsan.genericadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahsan.genericadapter.databinding.ItemSampleViewBinding


sealed class AdapterItem {
    abstract val viewType: Int
}

class CofeGenericAdapter(
    items: MutableList<AdapterItem>,
    private val viewHolderProviders: Map<Int, (ViewGroup) -> BaseViewHolder<AdapterItem>>
) : RecyclerView.Adapter<BaseViewHolder<AdapterItem>>() {

    private var items: MutableList<AdapterItem> = items.toMutableList()
    private var originalItems: MutableList<AdapterItem> = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AdapterItem> {
        val provider = viewHolderProviders[viewType]
            ?: throw IllegalArgumentException("No ViewHolderProvider registered for view type: $viewType")
        return provider(parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<AdapterItem>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<AdapterItem>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, newItems))
        this.items = newItems.toMutableList()
        this.originalItems = newItems.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    fun filter(predicate: (AdapterItem) -> Boolean) {
        val filteredItems = originalItems.filter(predicate)
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, filteredItems))
        items = filteredItems.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    private class DiffCallback<T>(
        private val oldList: List<T>,
        private val newList: List<T>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}


abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: T)
}

class FirstItemViewHolder(
    private val binding: ItemSampleViewBinding,
) : BaseViewHolder<AdapterItem>(binding.root) {
    override fun bind(data: AdapterItem) {
        if (data is FirstItem) {
            binding.sampleTextView.text = data.name
        }
    }

    companion object {
        fun createFirstItemViewHolder(parent: ViewGroup): FirstItemViewHolder {
            return FirstItemViewHolder(
                ItemSampleViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            )
        }
    }
}

class SecondItemViewHolder(
    private val binding: ItemSampleViewBinding,
) : BaseViewHolder<AdapterItem>(binding.root) {
    override fun bind(data: AdapterItem) {
        if (data is SecondItem) {
            binding.sampleTextView.text = data.name
            binding.sampleTextView.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.purple_200
                )
            )
        }
    }

    companion object {
        fun createSecondItemViewHolder(parent: ViewGroup): SecondItemViewHolder {
            return SecondItemViewHolder(
                ItemSampleViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            )
        }
    }
}
