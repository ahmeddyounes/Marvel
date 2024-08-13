package com.example.marvel.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvel.app.database.models.Character
import com.example.marvel.app.database.models.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): LiveData<List<Favorite>>

    @Query("SELECT * FROM character WHERE id IN (SELECT character FROM favorite) LIMIT :limit OFFSET :offset")
    fun getAllCharacters(limit: Int, offset: Int): LiveData<List<Character>>

    @Query("SELECT * FROM favorite WHERE id=:id LIMIT 1")
    fun getById(id: Int): LiveData<Favorite?>

    @Query("SELECT * FROM favorite WHERE character=:id LIMIT 1")
    fun getByCharacter(id: Int): LiveData<Favorite?>

    @Query("DELETE FROM favorite WHERE character=:id")
    fun deleteByCharacter(id: Int)

    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("DELETE FROM favorite")
    fun clear()
}