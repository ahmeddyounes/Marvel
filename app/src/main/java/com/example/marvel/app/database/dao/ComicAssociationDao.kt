package com.example.marvel.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvel.app.database.models.ComicAssociation

@Dao
interface ComicAssociationDao {
    @Query("SELECT * FROM comicassociation LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): LiveData<List<ComicAssociation>>

    @Query("SELECT * FROM comicassociation WHERE id=:id LIMIT 1")
    fun getById(id: Int): LiveData<ComicAssociation?>

    @Query("SELECT * FROM comicassociation WHERE character=:id")
    fun getByCharacter(id: Int): LiveData<List<ComicAssociation>>

    @Query("SELECT * FROM comicassociation WHERE comic=:id")
    fun getByComic(id: Int): LiveData<List<ComicAssociation>>

    @Insert
    fun insert(comicAssociation: ComicAssociation)

    @Delete
    fun delete(comicAssociation: ComicAssociation)

    @Query("DELETE FROM comicassociation")
    fun clear()
}