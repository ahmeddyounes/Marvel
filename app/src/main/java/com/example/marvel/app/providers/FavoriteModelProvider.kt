package com.example.marvel.app.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.marvel.app.database.dao.FavoriteDao
import com.example.marvel.app.database.models.Character
import com.example.marvel.app.database.models.Favorite
import com.example.marvel.app.exceptions.InvalidOperationException
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.collection.ICollectionProvider

class FavoriteModelProvider(
    private val dao: FavoriteDao
) : ICollectionProvider {
    fun save(favorite: Favorite) {
        try {
            dao.insert(favorite)
        } catch (_: Exception) {
        }
    }

    fun delete(favorite: Favorite) {
        try {
            dao.delete(favorite)
        } catch (_: Exception) {
        }
    }

    fun deleteByCharacter(id: Int) {
        try {
            dao.deleteByCharacter(id)
        } catch (_: Exception) {
        }
    }

    fun getById(id: Int): LiveData<Favorite?> {
        return dao.getById(id)
    }

    fun getAll(limit: Int, offset: Int): LiveData<List<Favorite>> {
        return dao.getAll(limit, offset)
    }

    fun getByCharacter(id: Int): LiveData<Favorite?> {
        return dao.getByCharacter(id)
    }

    fun getAllCharacters(limit: Int, offset: Int): LiveData<List<Character>> {
        return dao.getAllCharacters(limit, offset)
    }

    override fun getAllItems(limit: Int, offset: Int): LiveData<List<CollectionItem>> {
        return MediatorLiveData<List<CollectionItem>>().apply {
            addSource(getAllCharacters(limit, offset)) { result ->
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
        throw InvalidOperationException("Favorite model cannot have associations.")
    }
}