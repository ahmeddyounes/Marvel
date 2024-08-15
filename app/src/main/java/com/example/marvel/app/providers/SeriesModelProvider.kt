package com.example.marvel.app.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.marvel.app.database.models.Series
import com.example.marvel.app.database.models.SeriesAssociation

class SeriesModelProvider(
    private val local: AbstractSeriesModelProvider,
    private val network: AbstractSeriesModelProvider
) : AbstractSeriesModelProvider() {
    override fun getById(id: Int): LiveData<Series?> {
        return MediatorLiveData<Series?>().apply {
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

    override fun getAll(limit: Int, offset: Int): LiveData<List<Series>> {
        return MediatorLiveData<List<Series>>().apply {
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

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Series>> {
        return MediatorLiveData<List<Series>>().apply {
            addSource(network.getAssociated(id, limit, offset)) { result ->
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

    private fun storeAssociation(id: Int, series: List<Series>) {
        for (item in series) {
            saveAssociation(SeriesAssociation(character = id, series = item.id))
        }
    }

    private fun store(series: List<Series>) {
        for (item in series) {
            save(item)
        }
    }

    override fun save(series: Series) {
        local.save(series)
    }

    override fun delete(series: Series) {
        local.delete(series)
    }

    override fun saveAssociation(seriesAssociation: SeriesAssociation) {
        local.saveAssociation(seriesAssociation)
    }

    override fun deleteAssociation(seriesAssociation: SeriesAssociation) {
        local.deleteAssociation(seriesAssociation)
    }
}