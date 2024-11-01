package com.permana.xsisassessment.feature

import androidx.lifecycle.ViewModel
import com.permana.xsisassessment.core.data.XsisResponse
import com.permana.xsisassessment.core.data.api.model.Movie
import com.permana.xsisassessment.core.data.api.model.MovieVideo
import com.permana.xsisassessment.core.data.api.repository.MovieRepository
import com.permana.xsisassessment.core.utils.extenstion.getLaunch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll

class MainViewModel(val movieRepository: MovieRepository): ViewModel() {
    private val _movie = MutableSharedFlow<XsisResponse<Movie>>()
    val movie = _movie.asSharedFlow()

    private val _movieLatest = MutableSharedFlow<XsisResponse<Movie>>()
    val movieLatest = _movieLatest.asSharedFlow()

    private val _moviePopular = MutableSharedFlow<XsisResponse<Movie>>()
    val moviePopular = _moviePopular.asSharedFlow()

    private val _searchMovie = MutableSharedFlow<XsisResponse<Movie>>()
    val searchMovie = _searchMovie.asSharedFlow()

    private val _videoCode = MutableSharedFlow<XsisResponse<MovieVideo>>()
    val videoCode = _videoCode.asSharedFlow()


    fun fetchMovie() {
        getLaunch {
           _movie.emitAll(movieRepository.fetchMovie())
        }
    }

    fun fetchMovieLatest() {
        getLaunch {
            _movieLatest.emitAll(movieRepository.fetchMovieLatest())
        }
    }

    fun fetchMoviePopular() {
        getLaunch {
            _moviePopular.emitAll(movieRepository.fetchMoviePopular())
        }
    }

    fun searchMovie(name: String) {
        getLaunch {
            _searchMovie.emitAll(movieRepository.searchMovie(name))
        }
    }

    fun fetchVideoCode(id: Long) {
        getLaunch {
            _videoCode.emitAll(movieRepository.fetchVideoCode(id))
        }
    }
}