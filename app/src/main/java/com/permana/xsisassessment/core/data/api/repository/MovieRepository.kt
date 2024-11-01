package com.permana.xsisassessment.core.data.api.repository

import com.permana.xsisassessment.core.data.XsisResponse
import com.permana.xsisassessment.core.data.api.model.Movie
import com.permana.xsisassessment.core.data.api.model.MovieVideo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    /** fetch movie data from api **/
    suspend fun fetchMovie(): Flow<XsisResponse<Movie>>

    /** fetch movie latest data from api **/
    suspend fun fetchMovieLatest(): Flow<XsisResponse<Movie>>

    /** fetch movie data from api **/
    suspend fun fetchMoviePopular(): Flow<XsisResponse<Movie>>

    /** search movie from api **/
    suspend fun searchMovie(name: String): Flow<XsisResponse<Movie>>

    /** fetch video code **/
    suspend fun fetchVideoCode(id: Long): Flow<XsisResponse<MovieVideo>>

}