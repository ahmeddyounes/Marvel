package com.example.marvel.app.viewmodels.search

import androidx.lifecycle.LiveData
import com.example.marvel.app.viewmodels.collection.CollectionItem

interface ISearchProvider {
    fun searchItems(text: String, limit: Int, offset: Int): LiveData<List<CollectionItem>>
}