package com.example.marvel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.marvel.app.viewmodels.MainViewModel
import com.example.marvel.app.viewmodels.collection.CollectionType
import com.example.marvel.app.viewmodels.search.SearchType
import com.example.marvel.app.views.MainView
import com.example.marvel.ui.theme.MarvelTheme

class MainActivity : ComponentActivity() {
    private val view: MainView by lazy {
        MainView(
            MainViewModel(),
            onSearchClick = this::onSearchClick,
            onCharactersClick = this::onCharactersClick,
            onComicsClick = this::onComicsClick,
            onEventsClick = this::onEventsClick,
            onSeriesClick = this::onSeriesClick,
            onStoriesClick = this::onStoriesClick,
            onFavoritesClick = this::onFavoritesClick
        )
    }

    private fun startCollection(type: CollectionType) {
        val intent = Intent(this, CollectionActivity::class.java)
        intent.putExtra("type", type.name)
        startActivity(intent)
    }

    private fun onFavoritesClick() {
        startCollection(CollectionType.Favorite)
    }

    private fun onStoriesClick() {
        startCollection(CollectionType.Stories)
    }

    private fun onSeriesClick() {
        startCollection(CollectionType.Series)
    }

    private fun onEventsClick() {
        startCollection(CollectionType.Events)
    }

    private fun onCharactersClick() {
        startCollection(CollectionType.Characters)
    }

    private fun onComicsClick() {
        startCollection(CollectionType.Comics)
    }

    private fun onSearchClick() {
        val intent = Intent(this, SearchActivity::class.java)
        intent.putExtra("type", SearchType.Characters.name)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MarvelTheme {
                view.Render()
            }
        }
    }
}
