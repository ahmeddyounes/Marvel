package com.example.marvel.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.app.database.models.Favorite
import com.example.marvel.app.providers.FavoriteModelProvider

class FavoriteViewModel(
    private val modelProvider: FavoriteModelProvider,
    val id: Int = 0
) : ViewModel() {
    fun get(): LiveData<Favorite?> {
        return getByCharacter(id)
    }

    fun getById(id: Int): LiveData<Favorite?> {
        return modelProvider.getById(id)
    }

    fun getAll(limit: Int, offset: Int): LiveData<List<Favorite>> {
        return modelProvider.getAll(limit, offset)
    }

    fun getByCharacter(id: Int): LiveData<Favorite?> {
        return modelProvider.getByCharacter(id)
    }

    fun update(state: Boolean) {
        if (state) {
            modelProvider.save(Favorite(character = id))
        } else {
            modelProvider.deleteByCharacter(id)
        }
    }
}