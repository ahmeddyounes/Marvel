package com.example.marvel.app.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.marvel.app.database.models.Event
import com.example.marvel.app.database.models.EventAssociation
import com.example.marvel.app.providers.local.LocalEventModelProvider
import com.example.marvel.app.providers.network.NetworkEventModelProvider
import com.example.marvel.app.viewmodels.collection.CollectionItem

class EventModelProvider(
    private val local: LocalEventModelProvider,
    private val network: NetworkEventModelProvider
) : AbstractEventModelProvider() {
    override fun getById(id: Int): LiveData<Event?> {
        return MediatorLiveData<Event?>().apply {
            addSource(local.getById(id)) { result ->
                if (result != null) {
                    value = result
                } else {
                    addSource(network.getById(id)) { networkResult ->
                        if (networkResult != null) {
                            local.save(networkResult)
                        }
                        value = networkResult
                    }
                }
            }
        }
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Event>> {
        return MediatorLiveData<List<Event>>().apply {
            addSource(network.getAll(limit, offset)) { result ->
                if (result.isNotEmpty()) {
                    store(result)
                    value = result
                } else {
                    addSource(local.getAll(limit, offset)) { localResult ->
                        value = localResult
                    }
                }
            }
        }
    }

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Event>> {
        return MediatorLiveData<List<Event>>().apply {
            addSource(network.getAssociated(id, limit, offset)) { result ->
                if (result.isNotEmpty()) {
                    storeAssociation(id, result)
                    value = result
                } else {
                    addSource(local.getAssociated(id, limit, offset)) { localResult ->
                        value = localResult
                    }
                }
            }
        }
    }

    private fun storeAssociation(id: Int, events: List<Event>) {
        for (item in events) {
            saveAssociation(EventAssociation(character = id, event = item.id))
        }
    }

    private fun store(events: List<Event>) {
        for (item in events) {
            save(item)
        }
    }

    override fun save(event: Event) {
        local.save(event)
    }

    override fun delete(event: Event) {
        local.delete(event)
    }

    override fun saveAssociation(eventAssociation: EventAssociation) {
        local.saveAssociation(eventAssociation)
    }

    override fun deleteAssociation(eventAssociation: EventAssociation) {
        local.deleteAssociation(eventAssociation)
    }

    override fun getAllItems(limit: Int, offset: Int): LiveData<List<CollectionItem>> {
        return MediatorLiveData<List<CollectionItem>>().apply {
            addSource(getAll(limit, offset)) { result ->
                val list = mutableListOf<CollectionItem>()
                for (item in result) {
                    list.add(CollectionItem(item.thumbnail, item.title))
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
                    list.add(CollectionItem(item.thumbnail, item.title))
                }
                value = list
            }
        }
    }
}