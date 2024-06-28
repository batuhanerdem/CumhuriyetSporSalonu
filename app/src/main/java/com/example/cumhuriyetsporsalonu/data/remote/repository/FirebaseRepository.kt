package com.example.cumhuriyetsporsalonu.data.remote.repository

import android.util.Log
import com.example.cumhuriyetsporsalonu.domain.model.FirebaseLesson
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.CollectionName
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.LessonField
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.UserField
import com.example.cumhuriyetsporsalonu.domain.model.toVerifiedStatus
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import com.example.cumhuriyetsporsalonu.utils.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FirebaseRepository @Inject constructor(
    private val auth: FirebaseAuth, db: FirebaseFirestore
) {
    private val userCollectionRef = db.collection(CollectionName.USER.value)
    private val lessonCollectionRef = db.collection(CollectionName.LESSON.value)

    fun register(
        email: String,
        password: String,
        name: String,
        surname: String,
        callBack: (Resource<Uid>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            callBack(Resource.Loading())
            if (task.isSuccessful) {
                val result = task.result
                result.user?.let {
                    val myUser = User(it.uid, email, name, surname)
                    createUserForDB(myUser) { resource ->
                        callBack(resource)
                    }
                    return@addOnCompleteListener
                }
                callBack(Resource.Error(message = task.exception?.message?.stringfy()))
            } else callBack(Resource.Error(message = task.exception?.message?.stringfy()))
        }
    }

    fun signIn(
        email: String, password: String, callBack: (Resource<Uid>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            callBack(Resource.Loading())
            if (task.isSuccessful) {
                val result = task.result
                callBack(Resource.Success(result.user?.uid))
            } else callBack(Resource.Error(message = task.exception?.message?.stringfy()))
        }
    }

    fun setUser(user: User, callback: (Resource<Unit>) -> Unit) {
        userCollectionRef.document(user.uid).set(user.toHashMap()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(Resource.Success())
            } else callback(Resource.Error(message = task.exception?.message?.stringfy()))
        }
    }

    fun getUserByUid(uid: String, callback: (Resource<User>) -> Unit) {
        userCollectionRef.document(uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                if (result.get(UserField.UID.key) == null) {
                    callback(Resource.Error())
                    return@addOnCompleteListener
                }
                val myUser = convertDocumentToStudent(result)
                callback(Resource.Success(myUser))
            }
        }
    }

    fun getLessonsByStudentUid(studentUid: String, callback: (Resource<List<Lesson>>) -> Unit) {
        lessonCollectionRef.whereArrayContains(LessonField.STUDENT_UIDS.key, studentUid).get()
            .addOnCompleteListener { task ->
                callback(Resource.Loading())
                if (task.isSuccessful) {
                    val result = task.result
                    val lessonList = convertDocumentsToLessonList(result.documents)
                    callback(Resource.Success(lessonList))
                } else callback(Resource.Error(message = task.exception?.message?.stringfy()))
            }
    }


    private fun createUserForDB(user: User, callback: (Resource<Uid>) -> Unit) {
        userCollectionRef.document(user.uid).set(user.toHashMap()).addOnCompleteListener { task ->
            callback(Resource.Loading())
            if (task.isSuccessful) callback(Resource.Success(user.uid))
            else callback(Resource.Error())
        }
    }

    private fun convertDocumentToStudent(doc: DocumentSnapshot): User? {
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

    private fun convertDocumentsToLessonList(docs: List<DocumentSnapshot>): List<Lesson> {
        val lessonList = mutableListOf<Lesson>()
        docs.map {
            val lessonNullable = convertDocumentToLesson(it)
            lessonNullable?.let { lesson -> lessonList.add(lesson) }
        }
        return lessonList
    }

    private fun convertDocumentToLesson(doc: DocumentSnapshot): Lesson? {
        return try {
            val uid = doc.get(LessonField.UID.key) as String
            val name = doc.get(LessonField.NAME.key) as String
            val day = doc.get(LessonField.DAY.key) as Long
            val startHour = doc.get(LessonField.START_HOUR.key) as String
            val endHour = doc.get(LessonField.END_HOUR.key) as String
            val studentUids = doc.get(LessonField.STUDENT_UIDS.key) as List<String>
            val firebaseLesson =
                FirebaseLesson(uid, name, day.toInt(), startHour, endHour, studentUids)
            val lesson = firebaseLesson.toLesson()
            lesson
        } catch (e: Exception) {
            Log.d(TAG, "convertDocumentToLesson: $e ")
            null
        }
    }
}

typealias Uid = String