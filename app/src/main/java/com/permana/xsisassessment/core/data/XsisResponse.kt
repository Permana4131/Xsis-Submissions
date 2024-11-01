package com.permana.xsisassessment.core.data

sealed interface XsisResponse<out T> {
    object Loading : XsisResponse<Nothing>

    open class Error(
        open val message: String,
        val meta: Map<String, Any?> = mapOf(),
    ) : XsisResponse<Nothing>

    object Empty : XsisResponse<Nothing>

    class Success<T>(
        val data: T,
        val meta: Map<String, Any?> = mapOf(),
    ) : XsisResponse<T>
}