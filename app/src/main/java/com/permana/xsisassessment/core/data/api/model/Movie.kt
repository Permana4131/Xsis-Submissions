package com.permana.xsisassessment.core.data.api.model

data class Movie(
    val results: List<ResultsItem> = emptyList()
)

data class ResultsItem(
    val overview: String = "",
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val video: Boolean = false,
    val title: String = "",
    val genreIds: List<Int> = emptyList(),
    val posterPath: String = "",
    val backdropPath: String = "",
    val mediaType: String = "",
    val releaseDate: String = "",
    val popularity: Any = 0,
    val voteAverage: Any = 0,
    val id: Long = 0,
    val adult: Boolean = false,
    val voteCount: Int = 0
)