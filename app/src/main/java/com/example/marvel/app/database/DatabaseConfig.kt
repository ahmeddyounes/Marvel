package com.example.marvel.app.database

import android.content.Context
import androidx.room.Room

class DatabaseConfig(
    val name: String,
    val context: Context
) {
    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, name
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}