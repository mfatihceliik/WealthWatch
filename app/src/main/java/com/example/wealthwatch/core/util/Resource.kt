package com.example.wealthwatch.core.util

sealed interface Resource<out T> {
    object Loading : Resource<Nothing>
    data class Success<T>(val data: T, val message: String? = null) : Resource<T>
    data class Error(val message: String) : Resource<Nothing>
}

suspend inline fun <T, R> Resource<T>.mapData(
    crossinline transform: suspend (T) -> R
): Resource<R> = when (this) {
    is Resource.Success -> Resource.Success(transform(data), message)
    is Resource.Error -> Resource.Error(message)
    is Resource.Loading -> Resource.Loading
}
