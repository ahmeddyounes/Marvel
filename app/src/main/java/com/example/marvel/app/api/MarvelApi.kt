package com.example.marvel.app.api

import com.example.marvel.app.api.services.CharacterApiService
import com.example.marvel.app.api.services.ComicApiService
import com.example.marvel.app.api.services.EventApiService
import com.example.marvel.app.api.services.SeriesApiService
import com.example.marvel.app.api.services.StoryApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MarvelApi(private val config: MarvelApiConfig) {
    val character: CharacterApi by lazy {
        val api = build().create(CharacterApiService::class.java)
        CharacterApi(api, config)
    }

    val comic: ComicApi by lazy {
        val api = build().create(ComicApiService::class.java)
        ComicApi(api, config)
    }

    val event: EventApi by lazy {
        val api = build().create(EventApiService::class.java)
        EventApi(api, config)
    }

    val series: SeriesApi by lazy {
        val api = build().create(SeriesApiService::class.java)
        SeriesApi(api, config)
    }

    val story: StoryApi by lazy {
        val api = build().create(StoryApiService::class.java)
        StoryApi(api, config)
    }

    private fun build(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}