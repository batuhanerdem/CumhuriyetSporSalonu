package com.example.cumhuriyetsporsalonu.utils

import android.util.Log

sealed class Resource<T>(
    val data: T? = null, val message: Stringfy? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: Stringfy? = null, data: T? = null) : Resource<T>(data, message) {
        override fun toString(): String {
            return "Error(message=$message, data=$data)"
        }

        init {
            Log.d(TAG, this.toString())
        }
    }

    class Loading<T>(data: T? = null) : Resource<T>(data)
}