package com.example.marvel.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvel.app.database.models.Series

@Dao
interface SeriesDao {
    @Query("SELECT * FROM series LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): LiveData<List<Series>>

    @Query("SELECT * FROM series WHERE id=:id LIMIT 1")
    fun getById(id: Int): LiveData<Series?>

    @Query("SELECT * FROM series WHERE id IN (SELECT series FROM seriesassociation WHERE character=:id) LIMIT :limit OFFSET :offset")
    fun getAssociatedByCharacter(id: Int, limit: Int, offset: Int): LiveData<List<Series>>

    @Insert
    fun insert(series: Series)

    @Delete
    fun delete(series: Series)

    @Query("DELETE FROM series")
    fun clear()
}