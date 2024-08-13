package com.example.marvel.app.providers.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.marvel.app.api.EventApi
import com.example.marvel.app.api.responses.EventResponse
import com.example.marvel.app.database.models.Event
import com.example.marvel.app.database.models.EventAssociation
import com.example.marvel.app.exceptions.InvalidOperationException
import com.example.marvel.app.providers.AbstractEventModelProvider
import retrofit2.awaitResponse

class NetworkEventModelProvider(
    private val api: EventApi,
) : AbstractEventModelProvider() {
    override fun save(event: Event) {
        throw InvalidOperationException("Cannot save network models.")
    }

    override fun delete(event: Event) {
        throw InvalidOperationException("Cannot delete network models.")
    }

    override fun saveAssociation(eventAssociation: EventAssociation) {
        throw InvalidOperationException("Cannot save network models.")
    }

    override fun deleteAssociation(eventAssociation: EventAssociation) {
        throw InvalidOperationException("Cannot delete network models.")
    }

    private fun responseToModel(response: EventResponse): Event {
        val thumbnail = response.thumbnail.path + "." + response.thumbnail.extension
        return Event(
            response.id,
            response.title,
            response.description,
            Helper.convertToHttps(thumbnail)
        )
    }

    override fun getById(id: Int): LiveData<Event?> {
        return liveData {
            var event: Event? = null
            try {
                if (Helper.isNetworkAvailable()) {
                    val response = api.getById(id).awaitResponse()
                    val body = response.body()
                    val result = body?.data?.results
                    if (!result.isNullOrEmpty()) {
                        event = responseToModel(result[0])
                    }
                }
            } catch (_: Exception) {
            }
            emit(event)
        }
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Event>> {
        return liveData {
            val models = mutableListOf<Event>()
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

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Event>> {
        return liveData {
            val models = mutableListOf<Event>()
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