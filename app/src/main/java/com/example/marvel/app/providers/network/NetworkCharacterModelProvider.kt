package com.example.marvel.app.providers.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.marvel.app.api.CharacterApi
import com.example.marvel.app.api.responses.CharacterResponse
import com.example.marvel.app.database.models.Character
import com.example.marvel.app.exceptions.InvalidOperationException
import com.example.marvel.app.providers.AbstractCharacterModelProvider
import retrofit2.awaitResponse

class NetworkCharacterModelProvider(
    val api: CharacterApi
) : AbstractCharacterModelProvider() {
    override fun save(character: Character) {
        throw InvalidOperationException("Cannot save network models.")
    }

    override fun delete(character: Character) {
        throw InvalidOperationException("Cannot delete network models.")
    }

    private fun responseToModel(response: CharacterResponse): Character {
        val thumbnail = response.thumbnail.path + "." + response.thumbnail.extension
        return Character(
            response.id,
            response.name,
            response.description,
            Helper.convertToHttps(thumbnail)
        )
    }

    override fun getById(id: Int): LiveData<Character?> {
        return liveData {
            var character: Character? = null
            try {
                if (Helper.isNetworkAvailable()) {
                    val response = api.getById(id).awaitResponse()
                    val body = response.body()
                    val result = body?.data?.results

                    if (!result.isNullOrEmpty()) {
                        character = responseToModel(result[0])
                    }
                }
            } catch (_: Exception) {
            }
            emit(character)
        }
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Character>> {
        return liveData {
            val models = mutableListOf<Character>()
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

    override fun search(text: String, limit: Int, offset: Int): LiveData<List<Character>> {
        return liveData {
            val models = mutableListOf<Character>()

            if (Helper.isNetworkAvailable()) {
                try {
                    val response = api.search(text, limit, offset).awaitResponse()
                    val body = response.body()
                    val result = body?.data?.results
                    if (!result.isNullOrEmpty()) {
                        for (item in result) {
                            models.add(responseToModel(item))
                        }
                    }
                } catch (_: Exception) {
                }
            }

            emit(models)

        }
    }
}