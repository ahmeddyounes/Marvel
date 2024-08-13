package com.example.marvel.app

import com.example.marvel.app.api.MarvelApi
import com.example.marvel.app.database.AppDatabase
import com.example.marvel.app.providers.AbstractCharacterModelProvider
import com.example.marvel.app.providers.AbstractComicModelProvider
import com.example.marvel.app.providers.AbstractEventModelProvider
import com.example.marvel.app.providers.AbstractSeriesModelProvider
import com.example.marvel.app.providers.AbstractStoryModelProvider
import com.example.marvel.app.providers.FavoriteModelProvider

interface IApplication {
    fun getDatabase(): AppDatabase

    fun getCharacterModelProvider(): AbstractCharacterModelProvider
    fun setCharacterModelProvider(modelProvider: AbstractCharacterModelProvider)

    fun getComicModelProvider(): AbstractComicModelProvider
    fun setComicModelProvider(modelProvider: AbstractComicModelProvider)

    fun getEventModelProvider(): AbstractEventModelProvider
    fun setEventModelProvider(modelProvider: AbstractEventModelProvider)

    fun getSeriesModelProvider(): AbstractSeriesModelProvider
    fun setSeriesModelProvider(modelProvider: AbstractSeriesModelProvider)

    fun getStoryModelProvider(): AbstractStoryModelProvider
    fun setStoryModelProvider(modelProvider: AbstractStoryModelProvider)

    fun getFavoriteModelProvider(): FavoriteModelProvider
    fun setFavoriteModelProvider(modelProvider: FavoriteModelProvider)

    fun getMarvelApi(): MarvelApi
    fun setMarvelApi(api: MarvelApi)
}