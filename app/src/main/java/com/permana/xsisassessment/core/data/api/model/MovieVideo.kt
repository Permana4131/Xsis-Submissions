package com.permana.xsisassessment.core.data.api.model

data class MovieVideo(
	val results: List<ResultsItemVideo> =  emptyList()
)

data class ResultsItemVideo(
	val key: String? = null
)

