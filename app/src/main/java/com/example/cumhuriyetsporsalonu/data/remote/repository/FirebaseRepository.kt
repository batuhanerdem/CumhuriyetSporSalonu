package com.example.cumhuriyetsporsalonu.data.remote.repository

import com.example.cumhuriyetsporsalonu.domain.mappers.DocumentConverters.convertDocumentToLessonList
import com.example.cumhuriyetsporsalonu.domain.mappers.DocumentConverters.convertDocumentToStudent
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.CollectionName
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.LessonField
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.UserField
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FirebaseRepository @Inject constructor(
    private val auth: FirebaseAuth, db: FirebaseFirestore
) {
    private val userCollectionRef = db.collection(CollectionName.USER.value)
    private val lessonCollectionRef = db.collection(CollectionName.LESSON.value)


    fun registerWithEmailPassword(
        email: String, password: String, callBack: (Resource<Uid>) -> Unit
    ) {
        callBack(Resource.Loading())
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                callBack(Resource.Success(result.user?.uid))
            } else callBack(Resource.Error(message = task.exception?.message?.stringfy()))
        }
    }

    fun signIn(
        email: String, password: String, callBack: (Resource<Uid>) -> Unit
    ) {
        callBack(Resource.Loading())
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                callBack(Resource.Success(result.user?.uid))
            } else callBack(Resource.Error(message = task.exception?.message?.stringfy()))
        }
    }

    fun setUser(user: User, callback: (Resource<Nothing>) -> Unit) {
        callback(Resource.Loading())
        userCollectionRef.document(user.uid).set(user.toHashMap()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(Resource.Success())
            } else callback(Resource.Error(message = task.exception?.message?.stringfy()))
        }
    }

    fun getUserByUid(uid: String, callback: (Resource<User>) -> Unit) {
        callback(Resource.Loading())
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
        callback(Resource.Loading())
        lessonCollectionRef.whereArrayContains(LessonField.STUDENT_UIDS.key, studentUid)
            .orderBy(LessonField.DAY.key).orderBy(LessonField.START_HOUR.key).get()
            .addOnCompleteListener { task ->
                callback(Resource.Loading())
                if (task.isSuccessful) {
                    val result = task.result
                    val lessonList = convertDocumentToLessonList(result.documents)
                    callback(Resource.Success(lessonList))
                } else callback(Resource.Error(message = task.exception?.message?.stringfy()))
            }
    }
}

typealias Uid = String