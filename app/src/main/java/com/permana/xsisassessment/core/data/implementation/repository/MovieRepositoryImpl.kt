package com.permana.xsisassessment.core.data.implementation.repository

import android.util.Log
import com.permana.xsisassessment.core.data.XsisResponse
import com.permana.xsisassessment.core.data.api.model.Movie
import com.permana.xsisassessment.core.data.api.model.MovieVideo
import com.permana.xsisassessment.core.data.api.repository.MovieRepository
import com.permana.xsisassessment.core.data.implementation.mapper.toMovie
import com.permana.xsisassessment.core.data.implementation.mapper.toMovieVideo
import com.permana.xsisassessment.core.data.implementation.remote.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(val movieApi: MovieApi): MovieRepository {

    override suspend fun fetchMovie(): Flow<XsisResponse<Movie>> = flow {
        emit(XsisResponse.Loading)
        try {
            val response = movieApi.getList()
            when {
                response.success == false -> {
                    emit(XsisResponse.Error(message = response.statusMessage.orEmpty()))
                }

                response.results.isNullOrEmpty()  -> {
                    emit(XsisResponse.Empty)
                }

                else -> {
                    emit(XsisResponse.Success(response.toMovie()))
                }
            }
        } catch (e : Exception){
            emit(XsisResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchMovieLatest(): Flow<XsisResponse<Movie>> = flow {
        emit(XsisResponse.Loading)
        try {
            val response = movieApi.getListLatest()
            when {
                response.success == false -> {
                    emit(XsisResponse.Error(message = response.statusMessage.orEmpty()))
                }

                response.results.isNullOrEmpty()  -> {
                    emit(XsisResponse.Empty)
                }

                else -> {
                    emit(XsisResponse.Success(response.toMovie()))
                }
            }
        } catch (e : Exception){
            emit(XsisResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchMoviePopular(): Flow<XsisResponse<Movie>> = flow {
        emit(XsisResponse.Loading)
        try {
            val response = movieApi.getListPopular()
            when {
                response.success == false -> {
                    emit(XsisResponse.Error(message = response.statusMessage.orEmpty()))
                }

                response.results.isNullOrEmpty() -> {
                    emit(XsisResponse.Empty)
                }

                else -> {
                    emit(XsisResponse.Success(response.toMovie()))
                }
            }
        } catch (e : Exception){
            emit(XsisResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchMovie(name: String): Flow<XsisResponse<Movie>> = flow {
        emit(XsisResponse.Loading)
        try {
            val response = movieApi.searchMovie(name)
            when {
                response.success == false -> {
                    emit(XsisResponse.Error(message = response.statusMessage.orEmpty()))
                }

                response.results.isNullOrEmpty() -> {
                    emit(XsisResponse.Empty)
                }

                else -> {
                    emit(XsisResponse.Success(response.toMovie()))
                }
            }
        } catch (e : Exception){
            emit(XsisResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchVideoCode(id: Long): Flow<XsisResponse<MovieVideo>> = flow {
        emit(XsisResponse.Loading)
        try {
            val response = movieApi.movieVideo(id = id)
            when {
                response.success == false -> {
                    emit(XsisResponse.Error(message = "Someting Error"))
                }

                response.results.isNullOrEmpty() -> {
                    emit(XsisResponse.Empty)
                }

                else -> {
                    emit(XsisResponse.Success(response.toMovieVideo()))
                }
            }
        } catch (e : Exception){
            emit(XsisResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)
}