package com.example.marvel.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StoryAssociation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val story: Int,
    val character: Int,
)

@Entity
data class Story(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: String
)
