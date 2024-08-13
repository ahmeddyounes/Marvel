package com.example.marvel.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ComicAssociation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val comic: Int,
    val character: Int
)

@Entity
data class Comic(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: String
)
