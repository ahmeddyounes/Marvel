package com.example.marvel.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvel.app.database.models.Comic

@Dao
interface ComicDao {
    @Query("SELECT * FROM comic LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): LiveData<List<Comic>>

    @Query("SELECT * FROM comic WHERE id=:id LIMIT 1")
    fun getById(id: Int): LiveData<Comic?>

    @Query("SELECT * FROM comic WHERE id IN (SELECT comic FROM comicassociation WHERE character=:id) LIMIT :limit OFFSET :offset")
    fun getAssociatedByCharacter(id: Int, limit: Int, offset: Int): LiveData<List<Comic>>

    @Insert
    fun insert(comic: Comic)

    @Delete
    fun delete(comic: Comic)

    @Query("DELETE FROM comic")
    fun clear()
}