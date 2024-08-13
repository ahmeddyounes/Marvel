package com.example.marvel.app.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.marvel.app.database.models.Character
import com.example.marvel.app.providers.local.LocalCharacterModelProvider
import com.example.marvel.app.providers.network.NetworkCharacterModelProvider

class CharacterModelProvider(
    private val local: LocalCharacterModelProvider,
    private val network: NetworkCharacterModelProvider
) : AbstractCharacterModelProvider() {
    override fun getById(id: Int): LiveData<Character?> {
        return MediatorLiveData<Character?>().apply {
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

    override fun getAll(limit: Int, offset: Int): LiveData<List<Character>> {
        return MediatorLiveData<List<Character>>().apply {
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

    override fun search(text: String, limit: Int, offset: Int): LiveData<List<Character>> {
        return local.search(text, limit, offset)
        return MediatorLiveData<List<Character>>().apply {
            addSource(network.search(text, limit, offset)) { result ->
                if (result.isNotEmpty()) {
                    store(result)
                    value = result
                } else {
                    addSource(local.search(text, limit, offset)) { localResult ->
                        value = localResult
                    }
                }
            }
        }
    }

    private fun store(characters: List<Character>) {
        for (item in characters) {
            save(item)
        }
    }

    override fun save(character: Character) {
        local.save(character)
    }

    override fun delete(character: Character) {
        local.delete(character)
    }
}