package com.kristallik.jokeapp.network

import com.kristallik.jokeapp.data.JokeListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApiService {
    @GET("joke/Any")
    suspend fun getJokes(
        @Query("blacklistFlags") blacklistFlags: String = "nsfw,religious,political,racist,sexist,explicit",
        @Query("type") type: String = "twopart",
        @Query("amount") amount: Int = 10,
        @Query("page") page: Int? = null // Добавьте параметр страницы
    ): JokeListResponse
}