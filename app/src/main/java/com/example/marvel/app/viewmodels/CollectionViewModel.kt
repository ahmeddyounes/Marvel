package com.example.marvel.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.collection.ICollectionProvider
import kotlinx.coroutines.launch

class CollectionViewModel(
    val id: Int,
    val collection: ICollectionProvider
) : ViewModel() {
    private val _items = MutableLiveData<List<CollectionItem>>()
    val items: LiveData<List<CollectionItem>> get() = _items

    var currentPage = 0
    var isLoading = false
    var started = true
    private val limit = 2

    fun loadMore() {
        if (isLoading) return

        started = false
        isLoading = true

        viewModelScope.launch {
            val liveData: LiveData<List<CollectionItem>>
            if (id >= 0) {
                liveData = collection.getAssociatedItems(id, limit, currentPage * limit)
            } else {
                liveData = collection.getAllItems(limit, currentPage * limit)
            }

            liveData.observeForever { newItems ->
                val updatedItems = _items.value.orEmpty() + newItems
                _items.value = updatedItems
                isLoading = false
            }
            currentPage++
        }
    }
}