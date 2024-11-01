package com.permana.xsisassessment.core.data.implementation.remote

import com.permana.xsisassessment.core.data.implementation.response.MovieResponse
import com.permana.xsisassessment.core.data.implementation.response.MovieVideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("trending/movie/day?language=en-US")
    suspend fun getList(): MovieResponse

    @GET("movie/upcoming")
    suspend fun getListLatest(): MovieResponse

    @GET("movie/popular")
    suspend fun getListPopular(): MovieResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String?,
        @Query("include_adult") includeAdult: Boolean = false,
    ): MovieResponse

    @GET("movie/{id}/videos")
    suspend fun movieVideo(
        @Path("id") id: Long,
        @Query("language") language: String = "en-US",
    ): MovieVideoResponse
}