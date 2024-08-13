package com.example.marvel.app.viewmodels.item

import androidx.lifecycle.LiveData

class ItemHandler(
    val itemContentProvider: IItemContentProvider,
    val id: Int
) {
    private val viewLists = mutableListOf<ItemViewList>()

    fun addViewList(itemViewList: ItemViewList) {
        viewLists.add(itemViewList)
    }

    fun getViewLists(): List<ItemViewList> {
        return viewLists
    }

    fun getItemContent(): LiveData<ItemContent> {
        return itemContentProvider.getItemContent(id)
    }
}