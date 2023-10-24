# Kotlin RecyclerView Adapter with Clean Architecture

This project showcases a **generic adapter** for `RecyclerView` that adheres to **Clean Architecture** principles. The adapter can handle multiple view types efficiently and is structured to be clear and modular.

## Features:
- **Generic Adapter**: Can manage multiple view types.
- **Sealed Class for Adapter Items**: Using Kotlin's sealed class to define distinct item types for the adapter.
- **Coroutines**: Fetch mock data asynchronously without callbacks.

## Structure:

### AdapterItem Sealed Class
Define your view types and data classes as subclasses of `AdapterItem` to represent different item types.

### CofeGenericAdapter
A generic `RecyclerView` adapter handling multiple view types. Uses `viewHolderProviders` to map item types to their respective view holders.

### ViewHolders
Every `AdapterItem` subclass has a corresponding `ViewHolder`. These view holders inherit from a base `ViewHolder` class.

### MockFirstItemDataSource
A mock data source simulating a delay using Kotlin's coroutines and then provides a list of mock items.

## Setup:
1. Ensure Kotlin coroutines are added to your project.
2. Add the given classes and interfaces to your project.
3. Initialize the `CofeGenericAdapter` by passing in the desired initial list and a map of view type to view holder providers.

## Usage:

### 1. **Declare View Holders**

Each of your `AdapterItem` should have a corresponding view holder. These view holders should inherit from the `BaseViewHolder` class:

```kotlin
class FirstItemViewHolder(view: View) : BaseViewHolder<FirstItem>(view) {
    override fun bind(data: FirstItem) {
        // Example: Using view binding
        val binding = ItemFirstViewBinding.bind(view)
        binding.textView.text = data.name
    }

    companion object {
        fun create(parent: ViewGroup): FirstItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_first, parent, false)
            return FirstItemViewHolder(view)
        }
    }
}
```

### 2. **Initialize and Set Adapter in your Activity/Fragment**

To setup the `CofeGenericAdapter`, you need to initialize it with a list (can be empty initially) and provide the mapping for view types to their respective view holders:

```kotlin
val adapter = CofeGenericAdapter(
    items = mutableListOf(),
    viewHolderProviders = mapOf(
        FirstItem::class to { parent: ViewGroup -> FirstItemViewHolder.create(parent) }
        // Add mappings for other view types as needed.
    )
)
recyclerView.adapter = adapter
```

### 3. **Fetch and Update Data**

Once you've fetched data (e.g., from a ViewModel or some other data source), you can update the adapter:

```kotlin
viewModelScope.launch {
    val items = dataSource.fetchItems()
    // Update adapter with new items
    adapter.update(items)
}
