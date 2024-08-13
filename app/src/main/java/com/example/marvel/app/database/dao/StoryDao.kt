package com.example.marvel.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvel.app.database.models.Story

@Dao
interface StoryDao {
    @Query("SELECT * FROM story LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): LiveData<List<Story>>

    @Query("SELECT * FROM story WHERE id=:id LIMIT 1")
    fun getById(id: Int): LiveData<Story?>

    @Query("SELECT * FROM story WHERE id IN (SELECT story FROM storyassociation WHERE character=:id) LIMIT :limit OFFSET :offset")
    fun getAssociatedByCharacter(id: Int, limit: Int, offset: Int): LiveData<List<Story>>

    @Insert
    fun insert(story: Story)

    @Delete
    fun delete(story: Story)

    @Query("DELETE FROM story")
    fun clear()
}