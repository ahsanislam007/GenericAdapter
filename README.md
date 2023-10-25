# Kotlin RecyclerView Adapter with Clean Architecture

This project showcases a **generic adapter** for `RecyclerView` that adheres to **Clean Architecture** principles. The adapter can handle multiple view types efficiently and is structured to be clear and modular.

## Features:
- **Generic Adapter**: Can manage multiple view types.
- **Sealed Class for Adapter Items**: Using Kotlin's sealed class to define distinct item types for the adapter.

## Structure:

### AdapterItem Sealed Class
Define your view types and data classes as subclasses of `AdapterItem` to represent different item types.

### CofeGenericAdapter
A generic `RecyclerView` adapter handling multiple view types. Uses `viewHolderProviders` to map item types to their respective view holders.

### ViewHolders
Every `AdapterItem` subclass has a corresponding `ViewHolder`. These view holders inherit from a base `ViewHolder` class.

### DiffUtil Integration

The `CofeGenericAdapter` is built to seamlessly handle list updates with `DiffUtil`. By comparing old and new item lists, `DiffUtil` helps to identify changes and only updates the necessary items on the UI. This ensures smooth animations and efficient updates, especially for large lists:

```kotlin
fun update(newItems: List<AdapterItem>) {
        val oldItems = ArrayList(items)
        items.addAll(newItems)
        val diffResult = DiffUtil.calculateDiff(DiffCallback(oldItems, items))
        diffResult.dispatchUpdatesTo(this)
}
```

## Setup:
1. Ensure Kotlin coroutines are added to your project.
2. Add the given classes and interfaces to your project.
3. Initialize the `CofeGenericAdapter` by passing in the desired initial list and a map of view type to view holder providers.

## Usage:

### 1. **Extending `AdapterItem` for Model Classes**

For the `CofeGenericAdapter` to differentiate between different item types, it's essential to extend the `AdapterItem` sealed class and declare a unique view type for each model. This view type acts as an identifier to pair the item with its corresponding view holder:

### AdapterItem for Model Classes

```kotlin
sealed class AdapterItem {
    abstract val viewType: Int
}
```

### Extending `AdapterItem`

```kotlin
data class FirstItem(
    val id: Int,
    val name: String,
) : AdapterItem() {
    override val viewType: Int get() = FIRST_TYPE

    companion object {
        const val FIRST_TYPE = 1
    }
}
```

### 2. **Declare View Holders**

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
            val binding = ItemSampleViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return FirstItemViewHolder(binding)
        }
    }
}
```

### 3. **Initialize and Set Adapter in your Activity/Fragment**

To setup the `CofeGenericAdapter`, you need to initialize it with a list (can be empty initially) and provide the mapping for view types to their respective view holders:

```kotlin
val adapter = CofeGenericAdapter(
    items = mutableListOf(),
    viewHolderProviders = mapOf(
        FIRST_TYPE to { parent: ViewGroup -> FirstItemViewHolder.create(parent) }
        // Add mappings for other view types as needed.
    )
)
recyclerView.adapter = adapter
```

### 4. **Fetch and Update Data**

Once you've fetched data (e.g., from a ViewModel or some other data source), you can update the adapter:

```kotlin
viewModelScope.launch {
    val items = dataSource.fetchItems()
    // Update adapter with new items
    adapter.update(items)
}
```

### Pagination - Coming Soon!

In the roadmap for enhancing this adapter, **pagination** is a feature we're looking to incorporate next. This will allow the adapter to efficiently load and display large datasets in chunks, ensuring optimal performance and a smooth user experience:

1. **End of List Detection**: As the user scrolls and approaches the end of the loaded items, a signal will trigger the loading of the next set of items.
2. **Loading Indicators**: Integration with UI components to show the user when data is being fetched.
3. **Error Handling**: Mechanisms to handle failed data fetches, allowing for retries or displaying relevant error messages.

Stay tuned for updates and ensure you have the latest version to make use of this upcoming feature!
