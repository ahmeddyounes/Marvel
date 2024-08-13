package com.example.marvel.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvel.app.database.models.EventAssociation

@Dao
interface EventAssociationDao {
    @Query("SELECT * FROM eventassociation LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): LiveData<List<EventAssociation>>

    @Query("SELECT * FROM eventassociation WHERE id=:id LIMIT 1")
    fun getById(id: Int): LiveData<EventAssociation?>

    @Query("SELECT * FROM eventassociation WHERE character=:id")
    fun getByCharacter(id: Int): LiveData<List<EventAssociation>>

    @Query("SELECT * FROM eventassociation WHERE event=:id")
    fun getByEvent(id: Int): LiveData<List<EventAssociation>>

    @Insert
    fun insert(eventAssociation: EventAssociation)

    @Delete
    fun delete(eventAssociation: EventAssociation)

    @Query("DELETE FROM eventassociation")
    fun clear()
}