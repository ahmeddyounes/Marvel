package com.example.marvel.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.app.viewmodels.item.ItemContent
import com.example.marvel.app.viewmodels.item.ItemHandler
import com.example.marvel.app.viewmodels.item.ItemViewList

class ItemViewModel(
    private val itemHandler: ItemHandler
) : ViewModel() {
    fun getId(): Int {
        return itemHandler.id
    }

    fun getItemContent(): LiveData<ItemContent> {
        return itemHandler.getItemContent()
    }

    fun getViewLists(): List<ItemViewList> {
        return itemHandler.getViewLists()
    }
}