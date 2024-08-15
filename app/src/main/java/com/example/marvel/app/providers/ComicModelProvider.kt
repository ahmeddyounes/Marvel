package com.example.marvel.app.providers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.marvel.app.database.models.Comic
import com.example.marvel.app.database.models.ComicAssociation

class ComicModelProvider(
    private val local: AbstractComicModelProvider,
    private val network: AbstractComicModelProvider
) : AbstractComicModelProvider() {
    override fun getById(id: Int): LiveData<Comic?> {
        return MediatorLiveData<Comic?>().apply {
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

    override fun getAll(limit: Int, offset: Int): LiveData<List<Comic>> {
        return MediatorLiveData<List<Comic>>().apply {
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

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Comic>> {
        return MediatorLiveData<List<Comic>>().apply {
            addSource(network.getAssociated(id, limit, offset)) { result ->
                Log.v("RRRRr", result.toString())
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

    private fun storeAssociation(id: Int, comics: List<Comic>) {
        for (item in comics) {
            saveAssociation(ComicAssociation(character = id, comic = item.id))
        }
    }

    private fun store(comics: List<Comic>) {
        for (item in comics) {
            save(item)
        }
    }

    override fun save(comic: Comic) {
        local.save(comic)
    }

    override fun delete(comic: Comic) {
        local.delete(comic)
    }

    override fun saveAssociation(comicAssociation: ComicAssociation) {
        local.saveAssociation(comicAssociation)
    }

    override fun deleteAssociation(comicAssociation: ComicAssociation) {
        local.deleteAssociation(comicAssociation)
    }
}