package com.example.marvel.app.providers.local

import androidx.lifecycle.LiveData
import com.example.marvel.app.database.dao.EventAssociationDao
import com.example.marvel.app.database.dao.EventDao
import com.example.marvel.app.database.models.Event
import com.example.marvel.app.database.models.EventAssociation
import com.example.marvel.app.providers.AbstractEventModelProvider

class LocalEventModelProvider(
    private val dao: EventDao,
    private val associationDao: EventAssociationDao,
) : AbstractEventModelProvider() {
    override fun save(event: Event) {
        try {
            dao.insert(event)
        } catch (_: Exception) {

        }
    }

    override fun delete(event: Event) {
        dao.delete(event)
    }

    override fun saveAssociation(eventAssociation: EventAssociation) {
        try {
            associationDao.insert(eventAssociation)
        } catch (_: Exception) {

        }
    }

    override fun deleteAssociation(eventAssociation: EventAssociation) {
        associationDao.delete(eventAssociation)
    }

    override fun getById(id: Int): LiveData<Event?> {
        return dao.getById(id)
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Event>> {
        return dao.getAll(limit, offset)
    }

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Event>> {
        return dao.getAssociatedByCharacter(id, limit, offset)
    }
}