package com.example.marvel.app.api.services

import com.example.marvel.app.api.responses.MarvelResponse
import com.example.marvel.app.api.responses.StoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoryApiService {
    @GET("story")
    fun getAll(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
    ): Call<MarvelResponse<StoryResponse>>

    @GET("story/{id}")
    fun getById(
        @Path("id") id: Int,
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
    ): Call<MarvelResponse<StoryResponse>>

    @GET("characters/{id}/stories")
    fun getAssociated(
        @Path("id") id: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
    ): Call<MarvelResponse<StoryResponse>>
}