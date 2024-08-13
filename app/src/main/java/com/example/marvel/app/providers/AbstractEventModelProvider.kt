package com.example.marvel.app.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.marvel.app.database.models.Event
import com.example.marvel.app.database.models.EventAssociation
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.collection.ICollectionProvider
import com.example.marvel.app.viewmodels.item.IItemContentProvider
import com.example.marvel.app.viewmodels.item.ItemContent

abstract class AbstractEventModelProvider : ICollectionProvider, IItemContentProvider {
    abstract fun save(event: Event)
    abstract fun delete(event: Event)
    abstract fun saveAssociation(eventAssociation: EventAssociation)
    abstract fun deleteAssociation(eventAssociation: EventAssociation)

    abstract fun getById(id: Int): LiveData<Event?>
    abstract fun getAll(limit: Int, offset: Int): LiveData<List<Event>>
    abstract fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Event>>

    override fun getItemContent(id: Int): LiveData<ItemContent> {
        return MediatorLiveData<ItemContent>().apply {
            addSource(getById(id)) { result ->
                val item =
                    ItemContent(result!!.title, result.description as String, result.thumbnail)
                value = item
            }
        }
    }

    override fun getAllItems(limit: Int, offset: Int): LiveData<List<CollectionItem>> {
        return MediatorLiveData<List<CollectionItem>>().apply {
            addSource(getAll(limit, offset)) { result ->
                val list = mutableListOf<CollectionItem>()
                for (item in result) {
                    list.add(CollectionItem(item.thumbnail, item.title, item.id))
                }
                value = list
            }
        }
    }

    override fun getAssociatedItems(
        id: Int,
        limit: Int,
        offset: Int
    ): LiveData<List<CollectionItem>> {
        return MediatorLiveData<List<CollectionItem>>().apply {
            addSource(getAssociated(id, limit, offset)) { result ->
                val list = mutableListOf<CollectionItem>()
                for (item in result) {
                    list.add(CollectionItem(item.thumbnail, item.title, item.id))
                }
                value = list
            }
        }
    }
}