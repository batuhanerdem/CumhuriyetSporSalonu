package com.example.cumhuriyetsporsalonu.domain.mappers

import android.util.Log
import com.example.cumhuriyetsporsalonu.domain.mappers.VerifiedStatusMapper.toVerifiedStatus
import com.example.cumhuriyetsporsalonu.domain.model.FirebaseLesson
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.domain.model.Student
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.LessonField
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.UserField
import com.example.cumhuriyetsporsalonu.utils.TAG
import com.google.firebase.firestore.DocumentSnapshot

object DocumentConverters {
    fun convertDocumentToLessonList(
        docs: List<DocumentSnapshot>
    ): List<Lesson> {
        val lessonList = mutableListOf<Lesson>()
        docs.map {
            val lessonNullable = convertDocumentToLesson(it)
            lessonNullable?.let { lesson -> lessonList.add(lesson) }
        }
        return lessonList
    }

    fun convertDocumentToStudentList(
        documentSnapshot: List<DocumentSnapshot>
    ): MutableList<Student> {
        val list = mutableListOf<Student>()
        documentSnapshot.map {
            val student = convertDocumentToStudent(it)
            student?.let { list.add(it) }
        }

        return list
    }

    fun convertDocumentToStudent(doc: DocumentSnapshot): User? {
        return try {
            val uid = doc.get(UserField.UID.key) as String
            val email = doc.get(UserField.EMAIL.key) as String
            val name = doc.get(UserField.NAME.key) as String
            val surname = doc.get(UserField.SURNAME.key) as String?
            val age = doc.get(UserField.AGE.key) as String?
            val height = doc.get(UserField.HEIGHT.key) as String?
            val weight = doc.get(UserField.WEIGHT.key) as String?
            val lessonUids = doc.get(UserField.LESSON_UIDS.key) as List<String>
            val isVerified = doc.get(UserField.IS_VERIFIED.key) as String?
            val bmi = doc.get(UserField.BMI.key) as String?
            val isVerifiedTranslated = isVerified?.toVerifiedStatus()
            User(
                uid,
                email,
                name,
                surname,
                age,
                height,
                weight,
                bmi,
                isVerifiedTranslated!!,
                lessonUids
            )
        } catch (e: Exception) {
            Log.d(TAG, "convertDocumentToStudent: ${e}\n ${e.cause} message: ${e.message}")
            null
        }
    }


    fun convertDocumentToLesson(doc: DocumentSnapshot): Lesson? {
        return try {
            val uid = doc.get(LessonField.UID.key) as String
            val name = doc.get(LessonField.NAME.key) as String
            val day = doc.get(LessonField.DAY.key) as Long
            val startHour = doc.get(LessonField.START_HOUR.key) as String
            val endHour = doc.get(LessonField.END_HOUR.key) as String
            val studentUids = doc.get(LessonField.STUDENT_UIDS.key) as List<String>
            val requestUids = doc.get(LessonField.REQUEST_UIDS.key) as List<String>
            val firebaseLesson =
                FirebaseLesson(uid, name, day.toInt(), startHour, endHour, studentUids, requestUids)
            val lesson = firebaseLesson.toLesson()
            lesson
        } catch (e: Exception) {
            Log.d(TAG, "convertDocumentToLesson: $e ")
            null
        }
    }
}
