package com.example.marvel.app.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.marvel.app.components.AppCard
import com.example.marvel.app.components.Loading
import com.example.marvel.app.components.SingleRow
import com.example.marvel.app.viewmodels.CollectionViewModel
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.collection.ICollectionProvider

class CollectionView(
    private val collection: CollectionViewModel,
    private val name: String,
    private val onBackPressed: () -> Unit = {},
    private val onItemClicked: (CollectionItem, ICollectionProvider) -> Unit = { _: CollectionItem, _: ICollectionProvider ->
    }
) : AbstractView() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Header() {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.Black
            )
        )
    }

    @Composable
    fun Content() {
        val items by collection.items.observeAsState(initial = emptyList())
        val height = 160

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(items) { index, item ->
                if (index % 2 == 0) {
                    val nextItem = items.getOrNull(index + 1)
                    Column {
                        SingleRow {
                            AppCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(height.dp),
                                imageModifier = Modifier.height((height - 30).dp),
                                item = item,
                                onClick = {
                                    onItemClicked(item, collection.collection)
                                }
                            )
                            if (nextItem != null) {
                                AppCard(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(height.dp),
                                    imageModifier = Modifier.height((height - 30).dp),
                                    item = nextItem,
                                    onClick = {
                                        onItemClicked(nextItem, collection.collection)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                LaunchedEffect(Unit) {
                    collection.loadMore()
                }
                Loading(isLoading = collection.started)
            }
        }
    }

    @Composable
    override fun Render() {
        Column {
            Header()
            Content()
        }
    }
}