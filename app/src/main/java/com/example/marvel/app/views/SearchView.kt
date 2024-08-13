package com.example.marvel.app.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.marvel.app.components.SearchTextField
import com.example.marvel.app.viewmodels.SearchViewModel
import com.example.marvel.app.viewmodels.collection.CollectionItem

class SearchView(
    private val searchViewModel: SearchViewModel,
    private val onClose: () -> Unit = {}
) : AbstractView() {
    @Composable
    fun Search() {
        val searchResults by searchViewModel.searchResults.observeAsState(emptyList())

        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            SearchTextField(value = "", onValueChange = { v ->
                searchViewModel.updateSearchQuery(v)
            }, onClear = onClose)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(searchResults.size) { item ->
                    ResultItem(searchResults[item])
                }
            }
        }

    }

    @Composable
    fun ResultItem(item: CollectionItem) {
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    searchViewModel.onClick(item)
                })
                .padding(16.dp)
        ) {
            Text(text = item.text, style = MaterialTheme.typography.bodyMedium)
        }
    }

    @Composable
    override fun Render() {
        Search()
    }
}