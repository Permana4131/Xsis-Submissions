package com.permana.xsisassessment.core.data.implementation.response

import com.google.gson.annotations.SerializedName


data class MovieVideoResponse(
	@SerializedName("success")
	val success: Boolean? = null,
	val id: Int? = null,
	val results: List<ResultsItemVideoResponse>? = null,
)

data class ResultsItemVideoResponse(
	val key: String? = null
)

