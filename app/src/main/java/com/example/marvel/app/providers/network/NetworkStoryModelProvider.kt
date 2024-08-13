package com.example.marvel.app.providers.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.marvel.app.api.StoryApi
import com.example.marvel.app.api.responses.StoryResponse
import com.example.marvel.app.database.models.Story
import com.example.marvel.app.database.models.StoryAssociation
import com.example.marvel.app.exceptions.InvalidOperationException
import com.example.marvel.app.providers.AbstractStoryModelProvider
import retrofit2.awaitResponse

class NetworkStoryModelProvider(
    private val api: StoryApi,
) : AbstractStoryModelProvider() {
    override fun save(story: Story) {
        throw InvalidOperationException("Cannot save network models.")
    }

    override fun delete(story: Story) {
        throw InvalidOperationException("Cannot delete network models.")
    }

    override fun saveAssociation(storyAssociation: StoryAssociation) {
        throw InvalidOperationException("Cannot save network models.")
    }

    override fun deleteAssociation(storyAssociation: StoryAssociation) {
        throw InvalidOperationException("Cannot delete network models.")
    }

    private fun responseToModel(response: StoryResponse): Story {
        val thumbnail = response.thumbnail.path + "." + response.thumbnail.extension
        return Story(
            response.id,
            response.title,
            response.description,
            Helper.convertToHttps(thumbnail)
        )
    }

    override fun getById(id: Int): LiveData<Story?> {
        return liveData {
            var comic: Story? = null
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

    override fun getAll(limit: Int, offset: Int): LiveData<List<Story>> {
        return liveData {
            val models = mutableListOf<Story>()
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

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Story>> {
        return liveData {
            val models = mutableListOf<Story>()
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