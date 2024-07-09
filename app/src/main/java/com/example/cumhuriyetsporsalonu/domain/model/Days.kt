package com.example.cumhuriyetsporsalonu.domain.model

import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.utils.Stringfy
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy

enum class Days(val number: Int, val stringIdAsStringfy: Stringfy) {
    MONDAY(1, R.string.monday.stringfy()),
    TUESDAY(2, R.string.tuesday.stringfy()),
    WEDNESDAY(3, R.string.wednesday.stringfy()),
    THURSDAY(4, R.string.thursday.stringfy()),
    FRIDAY(5, R.string.friday.stringfy()),
    SATURDAY(6, R.string.saturday.stringfy()),
    SUNDAY(7, R.string.sunday.stringfy());

    companion object {
        fun Int.toDay(): Days? {
            return when (this) {
                1 -> MONDAY
                2 -> TUESDAY
                3 -> WEDNESDAY
                4 -> THURSDAY
                5 -> FRIDAY
                6 -> SATURDAY
                7 -> SUNDAY
                else -> null
            }
        }
    }
}
