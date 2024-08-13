package com.example.marvel.app.api.responses

data class MarvelDataResponse<T>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>
)