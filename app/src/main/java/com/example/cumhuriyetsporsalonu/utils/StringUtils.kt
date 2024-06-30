package com.example.cumhuriyetsporsalonu.utils

import java.util.Locale


object StringUtils {
    fun capitalizeFirstLetters(name: String?): String? {
        return name?.split(" ")?.joinToString(" ") {
            it.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        }
    }
}