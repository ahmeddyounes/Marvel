package com.example.marvel.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.marvel.app.api.MarvelApi
import com.example.marvel.app.database.AppDatabase
import com.example.marvel.app.database.DatabaseConfig
import com.example.marvel.app.providers.AbstractCharacterModelProvider
import com.example.marvel.app.providers.AbstractComicModelProvider
import com.example.marvel.app.providers.AbstractEventModelProvider
import com.example.marvel.app.providers.AbstractSeriesModelProvider
import com.example.marvel.app.providers.AbstractStoryModelProvider
import com.example.marvel.app.providers.FavoriteModelProvider

class MarvelApplication : Application(), IApplication {
    companion object {
        lateinit var instance: MarvelApplication

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

    private lateinit var marvelApi: MarvelApi
    private lateinit var characterModelProvider: AbstractCharacterModelProvider
    private lateinit var comicModelProvider: AbstractComicModelProvider
    private lateinit var eventModelProvider: AbstractEventModelProvider
    private lateinit var seriesModelProvider: AbstractSeriesModelProvider
    private lateinit var storyModelProvider: AbstractStoryModelProvider
    private lateinit var favoriteModelProvider: FavoriteModelProvider

    private lateinit var databaseConfig: DatabaseConfig

    override fun onCreate() {
        super.onCreate()

        instance = this

        databaseConfig = DatabaseConfig("marvel", applicationContext)
        val bootstrap = Bootstrap()
        bootstrap.init(this)
    }

    fun isNetworkAvailable(): Boolean {
        val context = applicationContext
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }


    override fun getDatabase(): AppDatabase {
        return databaseConfig.db
    }

    override fun getCharacterModelProvider(): AbstractCharacterModelProvider {
        return characterModelProvider
    }

    override fun setCharacterModelProvider(modelProvider: AbstractCharacterModelProvider) {
        this.characterModelProvider = modelProvider
    }

    override fun getComicModelProvider(): AbstractComicModelProvider {
        return comicModelProvider
    }

    override fun setComicModelProvider(modelProvider: AbstractComicModelProvider) {
        this.comicModelProvider = modelProvider
    }

    override fun getEventModelProvider(): AbstractEventModelProvider {
        return eventModelProvider
    }

    override fun setEventModelProvider(modelProvider: AbstractEventModelProvider) {
        this.eventModelProvider = modelProvider
    }

    override fun getSeriesModelProvider(): AbstractSeriesModelProvider {
        return seriesModelProvider
    }

    override fun setSeriesModelProvider(modelProvider: AbstractSeriesModelProvider) {
        this.seriesModelProvider = modelProvider
    }

    override fun getStoryModelProvider(): AbstractStoryModelProvider {
        return storyModelProvider
    }

    override fun setStoryModelProvider(modelProvider: AbstractStoryModelProvider) {
        this.storyModelProvider = modelProvider
    }

    override fun getFavoriteModelProvider(): FavoriteModelProvider {
        return favoriteModelProvider
    }

    override fun setFavoriteModelProvider(modelProvider: FavoriteModelProvider) {
        this.favoriteModelProvider = modelProvider
    }

    override fun getMarvelApi(): MarvelApi {
        return marvelApi
    }

    override fun setMarvelApi(api: MarvelApi) {
        this.marvelApi = api
    }
}