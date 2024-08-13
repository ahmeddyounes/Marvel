package com.example.marvel.app.providers.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.marvel.app.api.SeriesApi
import com.example.marvel.app.api.responses.SeriesResponse
import com.example.marvel.app.database.models.Series
import com.example.marvel.app.database.models.SeriesAssociation
import com.example.marvel.app.exceptions.InvalidOperationException
import com.example.marvel.app.providers.AbstractSeriesModelProvider
import retrofit2.awaitResponse

class NetworkSeriesModelProvider(
    private val api: SeriesApi,
) : AbstractSeriesModelProvider() {
    override fun save(series: Series) {
        throw InvalidOperationException("Cannot save network models.")
    }

    override fun delete(series: Series) {
        throw InvalidOperationException("Cannot delete network models.")
    }

    override fun saveAssociation(seriesAssociation: SeriesAssociation) {
        throw InvalidOperationException("Cannot save network models.")
    }

    override fun deleteAssociation(seriesAssociation: SeriesAssociation) {
        throw InvalidOperationException("Cannot delete network models.")
    }

    private fun responseToModel(response: SeriesResponse): Series {
        val thumbnail = response.thumbnail.path + "." + response.thumbnail.extension
        return Series(
            response.id,
            response.title,
            response.description,
            Helper.convertToHttps(thumbnail)
        )
    }

    override fun getById(id: Int): LiveData<Series?> {
        return liveData {
            var series: Series? = null
            try {
                if (Helper.isNetworkAvailable()) {
                    val response = api.getById(id).awaitResponse()
                    val body = response.body()
                    val result = body?.data?.results
                    if (!result.isNullOrEmpty()) {
                        series = responseToModel(result[0])
                    }
                }
            } catch (_: Exception) {
            }
            emit(series)
        }
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Series>> {
        return liveData {
            val models = mutableListOf<Series>()
            try {
                if (Helper.isNetworkAvailable()) {
                    val response = api.getAll(limit, offset).awaitResponse()
                    val body = response.body()
                    val result = body?.data?.results
                    if (!result.isNullOrEmpty()) {
                        for (item in result) {
                            models.add(responseToModel(item))
                        }
                    }
                }
            } catch (_: Exception) {
            }
            emit(models)
        }
    }

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Series>> {
        return liveData {
            val models = mutableListOf<Series>()
            try {
                if (Helper.isNetworkAvailable()) {
                    val response = api.getAssociated(id, limit, offset).awaitResponse()
                    val body = response.body()
                    val result = body?.data?.results
                    if (!result.isNullOrEmpty()) {
                        for (item in result) {
                            models.add(responseToModel(item))
                        }
                    }
                }
            } catch (_: Exception) {
            }
            emit(models)
        }
    }
}