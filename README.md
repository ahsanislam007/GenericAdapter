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

## CofeStateFlow:
We've also integrated `CofeStateFlow` which acts as a simple wrapper around `StateFlow` to manage UI states more effectively. This facilitates easy state updates and ensures that the latest state is always represented in your UI.

### MockFirstItemDataSource
A mock data source simulating a delay using Kotlin's coroutines and then provides a list of mock items.

## Setup:
1. Ensure Kotlin coroutines are added to your project.
2. Add the given classes and interfaces to your project.
3. Initialize the `CofeGenericAdapter` by passing in the desired initial list and a map of view type to view holder providers.

## Usage:
When you fetch data:
```kotlin
viewModelScope.launch {
    val items = dataSource.fetchItems()
    // Update UI
}
