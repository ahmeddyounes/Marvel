package com.example.marvel.app.providers.local

import androidx.lifecycle.LiveData
import com.example.marvel.app.database.dao.CharacterDao
import com.example.marvel.app.database.models.Character
import com.example.marvel.app.providers.AbstractCharacterModelProvider

class LocalCharacterModelProvider(private val dao: CharacterDao) :
    AbstractCharacterModelProvider() {
    override fun save(character: Character) {
        try {
            dao.insert(character)
        } catch (_: Exception) {

        }
    }

    fun clear() {
        dao.clear()
    }

    override fun delete(character: Character) {
        dao.delete(character)
    }

    override fun getById(id: Int): LiveData<Character?> {
        return dao.getById(id)
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Character>> {
        return dao.getAll(limit, offset)
    }

    override fun search(text: String, limit: Int, offset: Int): LiveData<List<Character>> {
        return dao.search("%$text%", limit, offset)
    }
}