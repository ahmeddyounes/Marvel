package com.example.marvel.app.providers.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.marvel.app.api.ComicApi
import com.example.marvel.app.api.responses.ComicResponse
import com.example.marvel.app.database.models.Comic
import com.example.marvel.app.database.models.ComicAssociation
import com.example.marvel.app.exceptions.InvalidOperationException
import com.example.marvel.app.providers.AbstractComicModelProvider
import retrofit2.awaitResponse

class NetworkComicModelProvider(
    private val api: ComicApi,
) : AbstractComicModelProvider() {
    override fun save(comic: Comic) {
        throw InvalidOperationException("Cannot save network models.")
    }

    override fun delete(comic: Comic) {
        throw InvalidOperationException("Cannot delete network models.")
    }

    override fun saveAssociation(comicAssociation: ComicAssociation) {
        throw InvalidOperationException("Cannot save network models.")
    }

    override fun deleteAssociation(comicAssociation: ComicAssociation) {
        throw InvalidOperationException("Cannot delete network models.")
    }

    private fun responseToModel(response: ComicResponse): Comic {
        val thumbnail = response.thumbnail.path + "." + response.thumbnail.extension
        return Comic(
            response.id,
            response.title,
            response.description,
            Helper.convertToHttps(thumbnail)
        )
    }

    override fun getById(id: Int): LiveData<Comic?> {
        return liveData {
            var comic: Comic? = null
            try {
                if (Helper.isNetworkAvailable()) {
                    val response = api.getById(id).awaitResponse()
                    val body = response.body()
                    val result = body?.data?.results
                    if (!result.isNullOrEmpty()) {
                        comic = responseToModel(result[0])
                    }
                }
            } catch (_: Exception) {
            }
            emit(comic)
        }
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Comic>> {
        return liveData {
            val models = mutableListOf<Comic>()
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

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Comic>> {
        return liveData {
            val models = mutableListOf<Comic>()
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