package com.example.marvel.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvel.app.database.models.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): LiveData<List<Event>>

    @Query("SELECT * FROM event WHERE id=:id LIMIT 1")
    fun getById(id: Int): LiveData<Event?>

    @Query("SELECT * FROM event WHERE id IN (SELECT event FROM eventassociation WHERE character=:id) LIMIT :limit OFFSET :offset")
    fun getAssociatedByCharacter(id: Int, limit: Int, offset: Int): LiveData<List<Event>>

    @Insert
    fun insert(event: Event)

    @Delete
    fun delete(event: Event)

    @Query("DELETE FROM event")
    fun clear()
}