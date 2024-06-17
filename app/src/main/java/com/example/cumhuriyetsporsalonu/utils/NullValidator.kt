package com.example.cumhuriyetsporsalonu.utils

object NullValidator {
    fun validate(vararg list: Any?): Boolean {
        for (item in list) {
            if (item == null) return false
        }
        return true
    }

}
