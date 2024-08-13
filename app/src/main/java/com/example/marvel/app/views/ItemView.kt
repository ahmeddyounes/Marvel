package com.example.marvel.app.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.marvel.app.components.CollectionViewList
import com.example.marvel.app.components.ItemDescription
import com.example.marvel.app.components.SearchPlaceholder
import com.example.marvel.app.viewmodels.CollectionViewModel
import com.example.marvel.app.viewmodels.FavoriteViewModel
import com.example.marvel.app.viewmodels.ItemViewModel
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.item.ItemViewList

class ItemView(
    private val itemViewModel: ItemViewModel,
    private val showSearch: Boolean = false,
    private val showFavorite: Boolean = false,
    private val favoriteViewModel: FavoriteViewModel,
    private val onSearchClicked: () -> Unit = {},
    private val onViewAllClicked: (ItemViewList) -> Unit = {},
    private val onItemClicked: (ItemViewList, CollectionItem) -> Unit = { _: ItemViewList, _: CollectionItem ->
    }
) : AbstractView() {
    @Composable
    private fun Item() {
        val item by itemViewModel.getItemContent().observeAsState()
        item?.let {
            if (showSearch) {
                SearchPlaceholder(placeholder = item!!.title, onClick = onSearchClicked)
            }
            if (showFavorite) {
                val favorite by favoriteViewModel.get().observeAsState()

                if (favorite != null) {
                    ItemDescription(
                        item = item!!,
                        showFavorite = true,
                        isFavorite = true,
                        onFavoriteClick = { value ->
                            favoriteViewModel.update(value)
                        })
                } else {
                    ItemDescription(
                        item = item!!,
                        showFavorite = true,
                        isFavorite = false,
                        onFavoriteClick = { value ->
                            favoriteViewModel.update(value)
                        })
                }
            } else {
                ItemDescription(item = item!!)
            }
        }

    }

    @Composable
    private fun CollectionList() {
        val lists = itemViewModel.getViewLists()

        for (list in lists) {
            CollectionViewList(
                list = list,
                collectionViewModel = CollectionViewModel(list.id, list.collection),
                onViewAllClick = onViewAllClicked,
                onItemClicked = onItemClicked
            )
        }
    }

    @Composable
    override fun Render() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Item()
            CollectionList()
        }
    }
}