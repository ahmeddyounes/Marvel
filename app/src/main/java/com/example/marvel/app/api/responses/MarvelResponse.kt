package com.example.marvel.app.api.responses

data class MarvelResponse<T>(
    val code: Int,
    val data: MarvelDataResponse<T>
)