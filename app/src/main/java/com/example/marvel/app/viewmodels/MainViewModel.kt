package com.example.marvel.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.marvel.app.viewmodels.collection.CollectionItem

class MainViewModel : ViewModel() {
    fun getLogoImageUrl(): LiveData<String> {
        return liveData {
            emit("https://i.annihil.us/u/prod/marvel/images/logo/marvel-logo.png")
        }
    }

    fun getSlideshowImages(): LiveData<List<String>> {
        return liveData {
            val list = listOf(
                "https://cdn.marvel.com/content/1x/thorthedarkworld_lob_mas_hlf_02.jpg",
                "https://cdn.marvel.com/content/1x/maguiregarfieldholland_opt.jpg",
                "https://cdn.marvel.com/content/1x/fox_x-menapocalypse_lob_crd_01.jpg",
                "https://cdn.marvel.com/content/1x/006hbb_ons_mas_mob_01_0.jpg"
            )
            emit(list)
        }
    }

    fun getCharacters(): CollectionItem {
        return CollectionItem(
            imageUrl = "https://cdn.marvel.com/u/prod/marvel/i/mg/3/a0/537ba3793915b/standard_incredible.jpg",
            text = "Characters"
        )
    }

    fun getComics(): CollectionItem {
        return CollectionItem(
            imageUrl = "https://cdn.marvel.com/content/1x/fcbd_ultimate_universe_cover_card.jpg",
            text = "Comics"
        )
    }

    fun getEvents(): CollectionItem {
        return CollectionItem(
            imageUrl = "https://cdn.marvel.com/u/prod/marvel/i/mg/2/a0/5ae09341531b2/rotator_xlarge_uncanny.jpg",
            text = "Events"
        )
    }

    fun getSeries(): CollectionItem {
        return CollectionItem(
            imageUrl = "https://cdn.marvel.com/content/1x/005smp_com_mas_mob_03_3.jpg",
            text = "Series"
        )
    }

    fun getStories(): CollectionItem {
        return CollectionItem(
            imageUrl = "https://cdn.marvel.com/content/1x/marvel_superheroes_ultimatepop_cvr.jpg",
            text = "Stories"
        )
    }
}