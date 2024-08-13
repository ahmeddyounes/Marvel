package com.example.marvel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.marvel.app.MarvelApplication
import com.example.marvel.app.viewmodels.SearchViewModel
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.item.ItemType
import com.example.marvel.app.viewmodels.search.SearchHandler
import com.example.marvel.app.viewmodels.search.SearchType
import com.example.marvel.app.views.SearchView
import com.example.marvel.ui.theme.MarvelTheme

class SearchActivity : ComponentActivity() {
    val type: SearchType by lazy {
        val type = intent.getStringExtra("type") as String
        SearchType.valueOf(type)
    }

    private val view: SearchView by lazy {
        SearchView(
            SearchViewModel(getSearchHandler()),
            onClose = this::onClose
        )
    }

    private fun getSearchHandler(): SearchHandler {
        val app = application as MarvelApplication
        var handler: SearchHandler? = null

        when (type) {
            SearchType.Characters -> handler = SearchHandler(
                app.getCharacterModelProvider(),
                this::onCharacterClicked
            )
        }
        return handler
    }

    private fun startItem(id: Int, type: ItemType) {
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("type", type.name)
        intent.putExtra("id", id)
        intent.putExtra("showSearch", true)
        startActivity(intent)
    }

    private fun onCharacterClicked(item: CollectionItem) {
        startItem(item.id, ItemType.Character)
    }

    private fun onClose() {
        finish()
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