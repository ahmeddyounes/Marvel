package com.example.marvel.app.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.marvel.app.database.models.Character
import com.example.marvel.app.exceptions.InvalidOperationException
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.collection.ICollectionProvider
import com.example.marvel.app.viewmodels.item.IItemContentProvider
import com.example.marvel.app.viewmodels.item.ItemContent
import com.example.marvel.app.viewmodels.search.ISearchProvider

abstract class AbstractCharacterModelProvider : ICollectionProvider, ISearchProvider,
    IItemContentProvider {
    abstract fun save(character: Character)
    abstract fun delete(character: Character)

    abstract fun getById(id: Int): LiveData<Character?>
    abstract fun getAll(limit: Int, offset: Int): LiveData<List<Character>>
    abstract fun search(text: String, limit: Int, offset: Int): LiveData<List<Character>>

    override fun getItemContent(id: Int): LiveData<ItemContent> {
        return MediatorLiveData<ItemContent>().apply {
            addSource(getById(id)) { result ->
                val item = ItemContent(result!!.name, result.description, result.thumbnail)
                value = item
            }
        }
    }

    override fun searchItems(
        text: String,
        limit: Int,
        offset: Int
    ): LiveData<List<CollectionItem>> {
        return MediatorLiveData<List<CollectionItem>>().apply {
            addSource(search(text, limit, offset)) { result ->
                val list = mutableListOf<CollectionItem>()
                for (item in result) {
                    list.add(CollectionItem(item.thumbnail, item.name, item.id))
                }
                value = list
            }
        }
    }

    override fun getAllItems(limit: Int, offset: Int): LiveData<List<CollectionItem>> {
        return MediatorLiveData<List<CollectionItem>>().apply {
            addSource(getAll(limit, offset)) { result ->
                val list = mutableListOf<CollectionItem>()
                for (item in result) {
                    list.add(CollectionItem(item.thumbnail, item.name, item.id))
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
        throw InvalidOperationException("Cannot access associations on characters.")
    }
}