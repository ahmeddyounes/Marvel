package com.example.marvel.app.viewmodels.collection

import androidx.lifecycle.LiveData

interface ICollectionProvider {
    fun getAllItems(limit: Int, offset: Int): LiveData<List<CollectionItem>>
    fun getAssociatedItems(id: Int, limit: Int, offset: Int): LiveData<List<CollectionItem>>
}