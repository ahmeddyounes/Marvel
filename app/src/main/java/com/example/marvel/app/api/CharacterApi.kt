package com.example.marvel.app.api

import com.example.marvel.app.api.responses.CharacterResponse
import com.example.marvel.app.api.responses.MarvelResponse
import com.example.marvel.app.api.services.CharacterApiService
import retrofit2.Call

class CharacterApi(
    private val apiService: CharacterApiService,
    config: MarvelApiConfig
) : AbstractApi(config) {
    fun getAll(limit: Int, offset: Int): Call<MarvelResponse<CharacterResponse>> {
        val timestamp = getTimestamp()
        val apiKey = getApiKey(timestamp)

        return apiService.getAll(limit, offset, timestamp, config.publicKey, apiKey)
    }

    fun search(text: String, limit: Int, offset: Int): Call<MarvelResponse<CharacterResponse>> {
        val timestamp = getTimestamp()
        val apiKey = getApiKey(timestamp)

        return apiService.search(text, limit, offset, timestamp, config.publicKey, apiKey)
    }

    fun getById(id: Int): Call<MarvelResponse<CharacterResponse>> {
        val timestamp = getTimestamp()
        val apiKey = getApiKey(timestamp)

        return apiService.getById(id, timestamp, config.publicKey, apiKey)
    }

}