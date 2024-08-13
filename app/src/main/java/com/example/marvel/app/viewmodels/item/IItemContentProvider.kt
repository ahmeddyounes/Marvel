package com.example.marvel.app.viewmodels.item

import androidx.lifecycle.LiveData

interface IItemContentProvider {
    fun getItemContent(id: Int): LiveData<ItemContent>
}