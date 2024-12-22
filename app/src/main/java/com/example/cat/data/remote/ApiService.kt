package com.example.cat.data.remote

import com.example.cat.data.remote.dto.CatDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("images/search")
    suspend fun getCats(
        @Query("size") size: String = "med",
        @Query("mime_types") mimeTypes: String = "jpg",
        @Query("format") format: String = "json",
        @Query("has_breeds") hasBreeds: Boolean = true,
        @Query("order") order: String = "ASC",
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10,
    ): List<CatDto>


}