package com.example.marvel.app.viewmodels.search

import androidx.lifecycle.LiveData
import com.example.marvel.app.viewmodels.collection.CollectionItem

class SearchHandler(
    private val searchProvider: ISearchProvider,
    private val onItemClick: (CollectionItem) -> Unit = {}
) : ISearchProvider {

    override fun searchItems(
        text: String,
        limit: Int,
        offset: Int
    ): LiveData<List<CollectionItem>> {
        return searchProvider.searchItems(text, limit, offset)
    }

    fun onClick(collectionItem: CollectionItem) {
        onItemClick(collectionItem)
    }
}