package com.example.marvel.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvel.app.database.dao.CharacterDao
import com.example.marvel.app.database.dao.ComicAssociationDao
import com.example.marvel.app.database.dao.ComicDao
import com.example.marvel.app.database.dao.EventAssociationDao
import com.example.marvel.app.database.dao.EventDao
import com.example.marvel.app.database.dao.FavoriteDao
import com.example.marvel.app.database.dao.SeriesAssociationDao
import com.example.marvel.app.database.dao.SeriesDao
import com.example.marvel.app.database.dao.StoryAssociationDao
import com.example.marvel.app.database.dao.StoryDao
import com.example.marvel.app.database.models.Character
import com.example.marvel.app.database.models.Comic
import com.example.marvel.app.database.models.ComicAssociation
import com.example.marvel.app.database.models.Event
import com.example.marvel.app.database.models.EventAssociation
import com.example.marvel.app.database.models.Favorite
import com.example.marvel.app.database.models.Series
import com.example.marvel.app.database.models.SeriesAssociation
import com.example.marvel.app.database.models.Story
import com.example.marvel.app.database.models.StoryAssociation

@Database(
    entities = [
        Character::class,
        Comic::class,
        Event::class,
        Series::class,
        Story::class,
        ComicAssociation::class,
        EventAssociation::class,
        SeriesAssociation::class,
        StoryAssociation::class,
        Favorite::class
    ], version = 5
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun comicDao(): ComicDao
    abstract fun eventDao(): EventDao
    abstract fun seriesDao(): SeriesDao
    abstract fun storyDao(): StoryDao
    abstract fun comicAssociationDao(): ComicAssociationDao
    abstract fun eventAssociationDao(): EventAssociationDao
    abstract fun seriesAssociationDao(): SeriesAssociationDao
    abstract fun storyAssociationDao(): StoryAssociationDao
    abstract fun favoriteDao(): FavoriteDao
}