package com.example.marvel.app.api

import com.example.marvel.app.api.responses.MarvelResponse
import com.example.marvel.app.api.responses.SeriesResponse
import com.example.marvel.app.api.services.SeriesApiService
import retrofit2.Call

class SeriesApi(
    private val apiService: SeriesApiService,
    config: MarvelApiConfig
) : AbstractApi(config) {
    fun getAll(limit: Int, offset: Int): Call<MarvelResponse<SeriesResponse>> {
        val timestamp = getTimestamp()
        val apiKey = getApiKey(timestamp)

        return apiService.getAll(limit, offset, timestamp, config.publicKey, apiKey)
    }

    fun getById(id: Int): Call<MarvelResponse<SeriesResponse>> {
        val timestamp = getTimestamp()
        val apiKey = getApiKey(timestamp)

        return apiService.getById(id, timestamp, config.publicKey, apiKey)
    }

    fun getAssociated(id: Int, limit: Int, offset: Int): Call<MarvelResponse<SeriesResponse>> {
        val timestamp = getTimestamp()
        val apiKey = getApiKey(timestamp)

        return apiService.getAssociated(id, limit, offset, timestamp, config.publicKey, apiKey)
    }
}