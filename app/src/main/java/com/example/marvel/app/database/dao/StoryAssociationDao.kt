package com.example.marvel.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvel.app.database.models.StoryAssociation

@Dao
interface StoryAssociationDao {
    @Query("SELECT * FROM storyassociation LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): LiveData<List<StoryAssociation>>

    @Query("SELECT * FROM storyassociation WHERE id=:id LIMIT 1")
    fun getById(id: Int): LiveData<StoryAssociation?>

    @Query("SELECT * FROM storyassociation WHERE character=:id")
    fun getByCharacter(id: Int): LiveData<List<StoryAssociation>>

    @Query("SELECT * FROM storyassociation WHERE story=:id")
    fun getByStory(id: Int): LiveData<List<StoryAssociation>>

    @Insert
    fun insert(storyAssociation: StoryAssociation)

    @Delete
    fun delete(storyAssociation: StoryAssociation)

    @Query("DELETE FROM storyassociation")
    fun clear()
}