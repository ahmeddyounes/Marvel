package com.example.marvel.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvel.app.viewmodels.CollectionViewModel
import com.example.marvel.app.viewmodels.collection.CollectionItem
import com.example.marvel.app.viewmodels.item.ItemViewList

@Composable
fun CollectionViewList(
    collectionViewModel: CollectionViewModel,
    list: ItemViewList,
    onViewAllClick: (ItemViewList) -> Unit = {},
    onItemClicked: (ItemViewList, CollectionItem) -> Unit = { _: ItemViewList, _: CollectionItem ->
    }
) {
    val items by collectionViewModel.items.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = list.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            TextButton(onClick = { onViewAllClick(list) }) {
                Text(
                    text = "View All",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.size) { i ->
                val item = items[i]
                AppCard(item = item,
                    modifier = Modifier
                        .weight(1f)
                        .height(130.dp)
                        .width(180.dp),
                    imageModifier = Modifier.height(100.dp),
                    onClick = {
                        onItemClicked(list, item)
                    }
                )
            }

            item {
                LaunchedEffect(Unit) {
                    collectionViewModel.loadMore()
                }
            }
        }
    }
}