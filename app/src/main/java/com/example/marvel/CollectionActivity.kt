package com.example.marvel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.marvel.app.MarvelApplication
import com.example.marvel.app.providers.AbstractCharacterModelProvider
import com.example.marvel.app.providers.AbstractComicModelProvider
import com.example.marvel.app.providers.AbstractEventModelProvider
import com.example.marvel.app.providers.AbstractSeriesModelProvider
import com.example.marvel.app.providers.AbstractStoryModelProvider
import com.example.marvel.app.providers.FavoriteModelProvider
import com.example.marvel.app.viewmodels.CollectionViewModel
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.collection.CollectionType
import com.example.marvel.app.viewmodels.collection.ICollectionProvider
import com.example.marvel.app.viewmodels.item.ItemType
import com.example.marvel.app.views.CollectionView
import com.example.marvel.ui.theme.MarvelTheme

class CollectionActivity : ComponentActivity() {
    private val id: Int by lazy {
        intent.getIntExtra("id", -1)
    }

    private val type: CollectionType by lazy {
        val type = intent.getStringExtra("type") as String
        CollectionType.valueOf(type)
    }

    private val title: String by lazy {
        val text: String = when (type) {
            CollectionType.Characters -> getString(R.string.characters_text)
            CollectionType.Comics -> getString(R.string.comics_text)
            CollectionType.Events -> getString(R.string.events_text)
            CollectionType.Series -> getString(R.string.series_text)
            CollectionType.Stories -> getString(R.string.stories_text)
            CollectionType.Favorite -> getString(R.string.favorites_text)
        }
        text
    }

    private val view: CollectionView by lazy {
        CollectionView(
            CollectionViewModel(
                id,
                getCollectionProvider()
            ),
            title,
            onBackPressed = this::onBack,
            onItemClicked = this::onItemClicked
        )
    }

    private fun startItem(id: Int, type: ItemType) {
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("type", type.name)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun onItemClicked(item: CollectionItem, collection: ICollectionProvider) {
        when (collection) {
            is AbstractCharacterModelProvider -> {
                startItem(item.id, ItemType.Character)
            }

            is FavoriteModelProvider -> {
                startItem(item.id, ItemType.Character)
            }

            is AbstractComicModelProvider -> {
                startItem(item.id, ItemType.Comic)
            }

            is AbstractEventModelProvider -> {
                startItem(item.id, ItemType.Event)
            }

            is AbstractSeriesModelProvider -> {
                startItem(item.id, ItemType.Series)
            }

            is AbstractStoryModelProvider -> {
                startItem(item.id, ItemType.Story)
            }
        }
    }

    private fun getCollectionProvider(): ICollectionProvider {
        val app = application as MarvelApplication

        val provider: ICollectionProvider = when (type) {
            CollectionType.Characters -> app.getCharacterModelProvider()
            CollectionType.Comics -> app.getComicModelProvider()
            CollectionType.Events -> app.getEventModelProvider()
            CollectionType.Series -> app.getSeriesModelProvider()
            CollectionType.Stories -> app.getStoryModelProvider()
            CollectionType.Favorite -> app.getFavoriteModelProvider()
        }
        return provider
    }

    private fun onBack() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(title)

        enableEdgeToEdge()
        setContent {
            MarvelTheme {
                view.Render()
            }
        }
    }
}