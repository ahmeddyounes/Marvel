package com.example.marvel.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvel.app.database.models.SeriesAssociation

@Dao
interface SeriesAssociationDao {
    @Query("SELECT * FROM seriesassociation LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): LiveData<List<SeriesAssociation>>

    @Query("SELECT * FROM seriesassociation WHERE id=:id LIMIT 1")
    fun getById(id: Int): LiveData<SeriesAssociation?>

    @Query("SELECT * FROM seriesassociation WHERE character=:id")
    fun getByCharacter(id: Int): LiveData<List<SeriesAssociation>>

    @Query("SELECT * FROM seriesassociation WHERE series=:id")
    fun getBySeries(id: Int): LiveData<List<SeriesAssociation>>

    @Insert
    fun insert(seriesAssociation: SeriesAssociation)

    @Delete
    fun delete(seriesAssociation: SeriesAssociation)

    @Query("DELETE FROM seriesassociation")
    fun clear()
}