package com.example.marvel.app

import com.example.marvel.AppConfig
import com.example.marvel.app.api.MarvelApi
import com.example.marvel.app.api.MarvelApiConfig
import com.example.marvel.app.providers.CharacterModelProvider
import com.example.marvel.app.providers.ComicModelProvider
import com.example.marvel.app.providers.EventModelProvider
import com.example.marvel.app.providers.FavoriteModelProvider
import com.example.marvel.app.providers.SeriesModelProvider
import com.example.marvel.app.providers.StoryModelProvider
import com.example.marvel.app.providers.local.LocalCharacterModelProvider
import com.example.marvel.app.providers.local.LocalComicModelProvider
import com.example.marvel.app.providers.local.LocalEventModelProvider
import com.example.marvel.app.providers.local.LocalSeriesModelProvider
import com.example.marvel.app.providers.local.LocalStoryModelProvider
import com.example.marvel.app.providers.network.NetworkCharacterModelProvider
import com.example.marvel.app.providers.network.NetworkComicModelProvider
import com.example.marvel.app.providers.network.NetworkEventModelProvider
import com.example.marvel.app.providers.network.NetworkSeriesModelProvider
import com.example.marvel.app.providers.network.NetworkStoryModelProvider

class Bootstrap {
    private fun getMarvelApiConfig(): MarvelApiConfig {
        return MarvelApiConfig(
            AppConfig.MARVEL_BASE_URL,
            AppConfig.MARVEL_PUBLIC_KEY,
            AppConfig.MARVEL_PRIVATE_KEY
        )
    }

    private fun setMarvelApi(application: IApplication) {
        val marvelApi = MarvelApi(getMarvelApiConfig())
        application.setMarvelApi(marvelApi)
    }

    private fun setCharacterModelProvider(application: IApplication) {
        val localProvider = LocalCharacterModelProvider(application.getDatabase().characterDao())
        val networkProvider = NetworkCharacterModelProvider(application.getMarvelApi().character)
        val provider = CharacterModelProvider(localProvider, networkProvider)
        application.setCharacterModelProvider(provider)
    }

    private fun setComicModelProvider(application: IApplication) {
        val localProvider = LocalComicModelProvider(
            application.getDatabase().comicDao(), application.getDatabase().comicAssociationDao()
        )
        val networkProvider = NetworkComicModelProvider(application.getMarvelApi().comic)
        val provider = ComicModelProvider(localProvider, networkProvider)
        application.setComicModelProvider(provider)
    }

    private fun setEventModelProvider(application: IApplication) {
        val localProvider = LocalEventModelProvider(
            application.getDatabase().eventDao(), application.getDatabase().eventAssociationDao()
        )
        val networkProvider = NetworkEventModelProvider(application.getMarvelApi().event)
        val provider = EventModelProvider(localProvider, networkProvider)
        application.setEventModelProvider(provider)
    }

    private fun setSeriesModelProvider(application: IApplication) {
        val localProvider = LocalSeriesModelProvider(
            application.getDatabase().seriesDao(), application.getDatabase().seriesAssociationDao()
        )
        val networkProvider = NetworkSeriesModelProvider(application.getMarvelApi().series)
        val provider = SeriesModelProvider(localProvider, networkProvider)
        application.setSeriesModelProvider(provider)
    }

    private fun setStoryModelProvider(application: IApplication) {
        val localProvider = LocalStoryModelProvider(
            application.getDatabase().storyDao(), application.getDatabase().storyAssociationDao()
        )
        val networkProvider = NetworkStoryModelProvider(application.getMarvelApi().story)
        val provider = StoryModelProvider(localProvider, networkProvider)
        application.setStoryModelProvider(provider)
    }

    private fun setFavoriteModelProvider(application: IApplication) {
        val provider = FavoriteModelProvider(application.getDatabase().favoriteDao())
        application.setFavoriteModelProvider(provider)
    }

    fun init(application: IApplication) {
        setMarvelApi(application)
        setComicModelProvider(application)
        setEventModelProvider(application)
        setSeriesModelProvider(application)
        setStoryModelProvider(application)
        setFavoriteModelProvider(application)
        setCharacterModelProvider(application)
    }
}