package com.example.marvel.app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun FavoriteButton(favorite: Boolean, onClick: (Boolean) -> Unit = {}) {
    var isFavorite by remember { mutableStateOf(favorite) }

    IconButton(onClick = {
        isFavorite = !isFavorite
        onClick(isFavorite)
    }) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Favorite",
            tint = if (isFavorite) Color.Red else Color.Gray
        )
    }
}

@Composable
fun FavoriteButtonPlaceholder(onClick: () -> Unit = {}) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Favorite",
            tint = Color.Gray
        )
    }
}