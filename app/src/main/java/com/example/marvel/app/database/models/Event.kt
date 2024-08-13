package com.example.marvel.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventAssociation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val event: Int,
    val character: Int,
)

@Entity
data class Event(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: String
)
