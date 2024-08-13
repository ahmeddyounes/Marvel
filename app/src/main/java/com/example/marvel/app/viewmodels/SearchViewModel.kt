package com.example.marvel.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.search.SearchHandler

class SearchViewModel(
    private val handler: SearchHandler
) : ViewModel() {
    private val _searchQuery = MutableLiveData<String>()

    private val _searchResults = MediatorLiveData<List<CollectionItem>>()
    val searchResults: LiveData<List<CollectionItem>> get() = _searchResults

    init {
        _searchResults.addSource(_searchQuery) { query ->
            query?.let {
                val newSource = handler.searchItems(query, 6, 0)
                _searchResults.addSource(newSource) { result ->
                    if (_searchQuery.value == query) {
                        _searchResults.value = result
                    }
                    _searchResults.removeSource(newSource) // Remove the old source after use
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun onClick(item: CollectionItem) {
        handler.onClick(item)
    }
}