package com.example.marvel.app.api.responses

data class EventResponse(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: ThumbnailResponse
)