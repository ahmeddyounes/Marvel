package com.example.marvel.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvel.app.database.models.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): LiveData<List<Character>>

    @Query("SELECT * FROM character WHERE name LIKE :text LIMIT :limit OFFSET :offset")
    fun search(text: String, limit: Int, offset: Int): LiveData<List<Character>>

    @Query("SELECT * FROM character WHERE id=:id LIMIT 1")
    fun getById(id: Int): LiveData<Character?>

    @Insert
    fun insert(character: Character)

    @Delete
    fun delete(character: Character)

    @Query("DELETE FROM character")
    fun clear()
}