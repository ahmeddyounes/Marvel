package com.example.marvel.app.api.responses

data class CharacterResponse(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailResponse
)