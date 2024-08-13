package com.example.marvel.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String
)
