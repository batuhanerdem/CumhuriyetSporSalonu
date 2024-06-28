package com.example.cumhuriyetsporsalonu.utils

object NullValidator {
    fun validate(vararg list: Any?): Boolean {
        list.map {
            if (it == null) return false
        }
        return true
    }

    fun <T> getNotNull(vararg list: T): List<T> {
        val myList = mutableListOf<T>()
        list.map {
            if (it != null) myList.add(it)
        }
        return myList
    }

}
