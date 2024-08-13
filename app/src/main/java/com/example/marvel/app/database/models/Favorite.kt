package com.example.marvel.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val character: Int
)
