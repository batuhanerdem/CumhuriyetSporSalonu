package com.example.cumhuriyetsporsalonu.domain.model

import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.domain.model.Days.Companion.toDay
import com.example.cumhuriyetsporsalonu.utils.LocalTimeConverter.toLocalTime
import com.example.cumhuriyetsporsalonu.utils.NullValidator

data class FirebaseLesson(
    val uid: String,
    val name: String,
    val day: Int,
    val startHour: String,
    val endHour: String,
    val studentUids: List<String> = emptyList(),
    val requestUids: List<String> = emptyList()
) {
    fun toLesson(): Lesson? {
        val startHourLocalTime = startHour.toLocalTime()
        val endHourLocalTime = endHour.toLocalTime()
        val dayEnum = this.day.toDay()
        val isValidated = NullValidator.validate(startHourLocalTime, endHourLocalTime, dayEnum)
        if (!isValidated) return null
        val lessonDate = LessonDate(dayEnum!!, startHourLocalTime!!, endHourLocalTime!!)
        return Lesson(
            uid, name, lessonDate, studentUids, requestUids
        )
    }

}