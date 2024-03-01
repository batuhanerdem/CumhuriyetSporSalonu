package com.example.cumhuriyetsporsalonu.utils

sealed class Resource<T>(
    val data: T? = null, val message: Stringfy? = null, val exception: Exception? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: Stringfy? = null, data: T? = null, exception: Exception? = null) :
        Resource<T>(data, message, exception)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}