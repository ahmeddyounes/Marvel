package com.example.marvel.app.viewmodels.item

import com.example.marvel.app.viewmodels.collection.ICollectionProvider

data class ItemViewList(
    val collection: ICollectionProvider,
    val name: String,
    val id: Int
)