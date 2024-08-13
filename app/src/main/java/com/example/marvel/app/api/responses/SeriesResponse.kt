package com.example.marvel.app.api.responses

data class SeriesResponse(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: ThumbnailResponse
)