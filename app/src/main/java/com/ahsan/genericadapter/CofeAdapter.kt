package com.ahsan.genericadapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahsan.genericadapter.databinding.ItemSampleViewBinding


sealed class AdapterItem {
    abstract val viewType: Int
}

class CofeGenericAdapter<VH : BaseViewHolder<AdapterItem>>(
    private var items: MutableList<AdapterItem>,
    private val viewHolderProviders: Map<Int, (ViewGroup) -> VH>
) : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val provider = viewHolderProviders[viewType]
            ?: throw IllegalArgumentException("No ViewHolderProvider registered for view type: $viewType")
        return provider(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<AdapterItem>) {
        val oldItems = ArrayList(items)
        items.addAll(newItems)
        val diffResult = DiffUtil.calculateDiff(DiffCallback(oldItems, items))
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
    val binding: ItemSampleViewBinding,
) : BaseViewHolder<FirstItem>(binding.root) {
    override fun bind(item: FirstItem) {
        binding.sampleTextView.text = item.name
    }
}

class SecondItemViewHolder(
    val binding: ItemSampleViewBinding,
) : BaseViewHolder<SecondItem>(binding.root) {
    override fun bind(item: SecondItem) {
        binding.sampleTextView.text = item.name
        binding.sampleTextView.setBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                R.color.purple_200
            )
        )
    }
}
