package com.example.marvel.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SeriesAssociation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val series: Int,
    val character: Int,
)

@Entity
data class Series(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: String
)
