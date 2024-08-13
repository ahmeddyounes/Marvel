package com.example.marvel.app.providers.local

import androidx.lifecycle.LiveData
import com.example.marvel.app.database.dao.SeriesAssociationDao
import com.example.marvel.app.database.dao.SeriesDao
import com.example.marvel.app.database.models.Series
import com.example.marvel.app.database.models.SeriesAssociation
import com.example.marvel.app.providers.AbstractSeriesModelProvider

class LocalSeriesModelProvider(
    private val dao: SeriesDao,
    private val associationDao: SeriesAssociationDao,
) : AbstractSeriesModelProvider() {
    override fun save(series: Series) {
        try {
            dao.insert(series)
        } catch (_: Exception) {

        }
    }

    override fun delete(series: Series) {
        dao.delete(series)
    }

    override fun saveAssociation(seriesAssociation: SeriesAssociation) {
        try {
            associationDao.insert(seriesAssociation)
        } catch (_: Exception) {

        }
    }

    override fun deleteAssociation(seriesAssociation: SeriesAssociation) {
        associationDao.delete(seriesAssociation)
    }

    override fun getById(id: Int): LiveData<Series?> {
        return dao.getById(id)
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Series>> {
        return dao.getAll(limit, offset)
    }

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Series>> {
        return dao.getAssociatedByCharacter(id, limit, offset)
    }
}