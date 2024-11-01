package com.permana.xsisassessment.core.data.implementation.mapper

import com.permana.xsisassessment.core.data.api.model.Movie
import com.permana.xsisassessment.core.data.api.model.MovieVideo
import com.permana.xsisassessment.core.data.api.model.ResultsItem
import com.permana.xsisassessment.core.data.api.model.ResultsItemVideo
import com.permana.xsisassessment.core.data.implementation.response.MovieResponse
import com.permana.xsisassessment.core.data.implementation.response.MovieVideoResponse
import com.permana.xsisassessment.core.data.implementation.response.ResultsItemResponse
import com.permana.xsisassessment.core.data.implementation.response.ResultsItemVideoResponse

fun MovieResponse.toMovie() = Movie(
    results = this.results?.map { it.toResultItem() } ?: emptyList()
)

fun ResultsItemResponse.toResultItem() = ResultsItem(
    overview = this.overview ?: "",
    originalLanguage = this.originalLanguage ?: "",
    originalTitle = this.originalTitle ?: "",
    video = this.video ?: false,
    title = this.title ?: "",
    genreIds = this.genreIds?.filterNotNull() ?: emptyList(),
    posterPath = this.posterPath ?: "",
    backdropPath = this.backdropPath ?: "",
    mediaType = this.mediaType ?: "",
    releaseDate = this.releaseDate ?: "",
    popularity = this.popularity ?: 0,
    voteAverage = this.voteAverage ?: 0,
    id = this.id ?: 0,
    adult = this.adult ?: false,
    voteCount = this.voteCount ?: 0
)

fun MovieVideoResponse.toMovieVideo() = MovieVideo(
    results = this.results?.map { it.toResultsItemVideo() } ?: emptyList()
)

fun ResultsItemVideoResponse.toResultsItemVideo() = ResultsItemVideo(
    key = this.key ?: ""
)