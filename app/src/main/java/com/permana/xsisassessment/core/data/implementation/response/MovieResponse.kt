package com.permana.xsisassessment.core.data.implementation.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
	@SerializedName("success")
	val success: Boolean? = null,

	@SerializedName("status_message")
	val statusMessage: String? = null,

	@SerializedName("results")
	val results: List<ResultsItemResponse>? = null,
)

data class ResultsItemResponse(
	@SerializedName("overview")
	val overview: String? = null,

	@SerializedName("original_language")
	val originalLanguage: String? = null,

	@SerializedName("original_title")
	val originalTitle: String? = null,

	@SerializedName("video")
	val video: Boolean? = null,

	@SerializedName("title")
	val title: String? = null,

	@SerializedName("genre_ids")
	val genreIds: List<Int?>? = null,

	@SerializedName("poster_path")
	val posterPath: String? = null,

	@SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@SerializedName("media_type")
	val mediaType: String? = null,

	@SerializedName("release_date")
	val releaseDate: String? = null,

	@SerializedName("popularity")
	val popularity: Any? = null,

	@SerializedName("vote_average")
	val voteAverage: Any? = null,

	@SerializedName("id")
	val id: Long? = null,

	@SerializedName("adult")
	val adult: Boolean? = null,

	@SerializedName("vote_count")
	val voteCount: Int? = null
)