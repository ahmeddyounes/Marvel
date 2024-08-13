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
import com.example.marvel.app.viewmodels.FavoriteViewModel
import com.example.marvel.app.viewmodels.ItemViewModel
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.collection.CollectionType
import com.example.marvel.app.viewmodels.item.ItemHandler
import com.example.marvel.app.viewmodels.item.ItemType
import com.example.marvel.app.viewmodels.item.ItemViewList
import com.example.marvel.app.views.ItemView
import com.example.marvel.ui.theme.MarvelTheme

class ItemActivity : ComponentActivity() {
    private val id: Int by lazy {
        intent.getIntExtra("id", 0)
    }

    private val type: ItemType by lazy {
        val type = intent.getStringExtra("type") as String
        ItemType.valueOf(type)
    }

    private val view: ItemView by lazy {
        ItemView(
            ItemViewModel(getItemHandler()),
            showSearch = intent.getBooleanExtra("showSearch", false),
            onSearchClicked = this::onSearchClicked,
            onViewAllClicked = this::onViewAllClicked,
            onItemClicked = this::onItemClicked,
            showFavorite = (type == ItemType.Character),
            favoriteViewModel = getFavoriteViewModel()
        )
    }

    private fun getFavoriteViewModel(): FavoriteViewModel {
        val app = application as MarvelApplication
        return FavoriteViewModel(
            app.getFavoriteModelProvider(),
            id
        )
    }

    private fun onItemClicked(list: ItemViewList, item: CollectionItem) {
        when (list.collection) {
            is AbstractCharacterModelProvider -> {
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

    private fun startItem(id: Int, type: ItemType) {
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("type", type.name)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun startCollection(type: CollectionType) {
        val intent = Intent(this, CollectionActivity::class.java)
        intent.putExtra("type", type.name)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun onViewAllClicked(itemViewList: ItemViewList) {
        when (itemViewList.collection) {
            is AbstractCharacterModelProvider -> {
                startCollection(CollectionType.Characters)
            }

            is AbstractComicModelProvider -> {
                startCollection(CollectionType.Comics)
            }

            is AbstractEventModelProvider -> {
                startCollection(CollectionType.Events)
            }

            is AbstractSeriesModelProvider -> {
                startCollection(CollectionType.Series)
            }

            is AbstractStoryModelProvider -> {
                startCollection(CollectionType.Stories)
            }
        }
    }

    private fun onSearchClicked() {
        finish()
    }

    private fun getComicViewList(app: MarvelApplication, id: Int): ItemViewList {
        return ItemViewList(
            app.getComicModelProvider(),
            "Comics",
            id
        )
    }

    private fun getEventViewList(app: MarvelApplication, id: Int): ItemViewList {
        return ItemViewList(
            app.getEventModelProvider(),
            "Events",
            id
        )
    }

    private fun getSeriesViewList(app: MarvelApplication, id: Int): ItemViewList {
        return ItemViewList(
            app.getSeriesModelProvider(),
            "Series",
            id
        )
    }

    private fun getStoriesViewList(app: MarvelApplication, id: Int): ItemViewList {
        return ItemViewList(
            app.getSeriesModelProvider(),
            "Stories",
            id
        )
    }

    private fun getItemHandler(): ItemHandler {
        val app = application as MarvelApplication
        val handler: ItemHandler

        when (type) {
            ItemType.Character -> {
                handler = ItemHandler(
                    app.getCharacterModelProvider(),
                    id
                )
                handler.addViewList(getComicViewList(app, id))
                handler.addViewList(getEventViewList(app, id))
                handler.addViewList(getSeriesViewList(app, id))
                handler.addViewList(getStoriesViewList(app, id))
            }

            ItemType.Comic -> {
                handler = ItemHandler(
                    app.getComicModelProvider(),
                    id
                )
            }

            ItemType.Event -> {
                handler = ItemHandler(
                    app.getEventModelProvider(),
                    id
                )
            }

            ItemType.Series -> {
                handler = ItemHandler(
                    app.getSeriesModelProvider(),
                    id
                )
            }

            ItemType.Story -> {
                handler = ItemHandler(
                    app.getStoryModelProvider(),
                    id
                )
            }
        }
        return handler
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
