package com.example.webtooninfoapp.data.remote

import com.example.webtooninfoapp.data.models.Main
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("manga/fetch")
    suspend fun fetchManga(
        @Query("page")page:Int
    ): Main
}