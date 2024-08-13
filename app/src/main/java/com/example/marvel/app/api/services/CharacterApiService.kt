package com.example.marvel.app.api.services

import com.example.marvel.app.api.responses.CharacterResponse
import com.example.marvel.app.api.responses.MarvelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiService {
    @GET("characters")
    fun getAll(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
    ): Call<MarvelResponse<CharacterResponse>>

    @GET("characters")
    fun search(
        @Query("nameStartsWith") text: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
    ): Call<MarvelResponse<CharacterResponse>>


    @GET("characters/{id}")
    fun getById(
        @Path("id") id: Int,
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
    ): Call<MarvelResponse<CharacterResponse>>
}