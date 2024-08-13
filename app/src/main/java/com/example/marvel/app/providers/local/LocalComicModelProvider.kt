package com.example.marvel.app.providers.local

import androidx.lifecycle.LiveData
import com.example.marvel.app.database.dao.ComicAssociationDao
import com.example.marvel.app.database.dao.ComicDao
import com.example.marvel.app.database.models.Comic
import com.example.marvel.app.database.models.ComicAssociation
import com.example.marvel.app.providers.AbstractComicModelProvider

class LocalComicModelProvider(
    private val dao: ComicDao,
    private val associationDao: ComicAssociationDao,
) : AbstractComicModelProvider() {
    override fun save(comic: Comic) {
        try {
            dao.insert(comic)
        } catch (_: Exception) {
        }
    }

    override fun delete(comic: Comic) {
        dao.delete(comic)
    }

    override fun saveAssociation(comicAssociation: ComicAssociation) {
        try {
            associationDao.insert(comicAssociation)
        } catch (_: Exception) {

        }
    }

    override fun deleteAssociation(comicAssociation: ComicAssociation) {
        associationDao.delete(comicAssociation)
    }

    override fun getById(id: Int): LiveData<Comic?> {
        return dao.getById(id)
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Comic>> {
        return dao.getAll(limit, offset)
    }

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Comic>> {
        return dao.getAssociatedByCharacter(id, limit, offset)
    }
}