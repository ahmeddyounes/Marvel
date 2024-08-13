package com.example.marvel.app.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.marvel.app.database.models.Story
import com.example.marvel.app.database.models.StoryAssociation
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.collection.ICollectionProvider
import com.example.marvel.app.viewmodels.item.IItemContentProvider
import com.example.marvel.app.viewmodels.item.ItemContent

abstract class AbstractStoryModelProvider : ICollectionProvider, IItemContentProvider {
    abstract fun save(story: Story)
    abstract fun delete(story: Story)
    abstract fun saveAssociation(storyAssociation: StoryAssociation)
    abstract fun deleteAssociation(storyAssociation: StoryAssociation)

    abstract fun getById(id: Int): LiveData<Story?>
    abstract fun getAll(limit: Int, offset: Int): LiveData<List<Story>>
    abstract fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Story>>

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