package com.example.cumhuriyetsporsalonu.data.remote.repository

import android.util.Log
import com.example.cumhuriyetsporsalonu.domain.mappers.DocumentConverters.convertDocumentToLessonList
import com.example.cumhuriyetsporsalonu.domain.mappers.DocumentConverters.convertDocumentToStudent
import com.example.cumhuriyetsporsalonu.domain.mappers.VerifiedStatusMapper.toVerifiedStatus
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.CollectionName
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.LessonField
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.UserField
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ViewModelScoped
class FirebaseRepository @Inject constructor(
    private val auth: FirebaseAuth, db: FirebaseFirestore
) {
    private val userCollectionRef = db.collection(CollectionName.USER.value)
    private val lessonCollectionRef = db.collection(CollectionName.LESSON.value)

    fun register(email: String, password: String): Flow<Resource<Uid>> = callbackFlow {
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    trySend(Resource.Success(result.user?.uid))
                } else {
                    trySend(Resource.Error(message = task.exception?.message?.stringfy()))
                }
            }.await()
        } catch (e: Exception) {
            Log.d("tag", "exception: ${e.message}")
            trySend(Resource.Error(e.message?.stringfy()))
        }

        awaitClose { this.cancel() }

    }

    fun signIn(email: String, password: String): Flow<Resource<Uid>> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                trySend(Resource.Success(result.user?.uid))
            } else {
                trySend(Resource.Error(message = task.exception?.message?.stringfy()))
            }
        }.await()
        awaitClose { this.cancel() }
    }

    fun setUser(user: User): Flow<Resource<Unit>> = callbackFlow {
        userCollectionRef.document(user.uid).set(user.toHashMap()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(Resource.Success())
            } else {
                trySend(Resource.Error(message = task.exception?.message?.stringfy()))
            }
        }.await()
        awaitClose { this.cancel() }
    }

    fun listenVerifiedStatus(id: String): Flow<Resource<VerifiedStatus>> = callbackFlow {
        val listenerRegistration =
            userCollectionRef.document(id).addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Resource.Error(message = error.message?.stringfy()))
                    return@addSnapshotListener
                }
                value?.let {
                    if (it.get(UserField.IS_VERIFIED.key) == null) trySend(Resource.Error())
                    else {
                        val statusString = it.get(UserField.IS_VERIFIED.key) as String
                        trySend(Resource.Success(statusString.toVerifiedStatus()))
                    }
                }
            }

        awaitClose {
            listenerRegistration.remove()
            this.cancel()
        }
    }

    fun getUserByUid(uid: String): Flow<Resource<User>> = callbackFlow {
        userCollectionRef.document(uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                if (result.get(UserField.UID.key) == null) {
                    trySend(Resource.Error())
                } else {
                    val myUser = convertDocumentToStudent(result)
                    trySend(Resource.Success(myUser))
                }
            } else {
                trySend(Resource.Error(message = task.exception?.message?.stringfy()))
            }
        }.await()
        awaitClose { this.cancel() }
    }

    fun getLessonsByStudentUid(studentUid: String): Flow<Resource<List<Lesson>>> = callbackFlow {
        lessonCollectionRef.whereArrayContains(LessonField.STUDENT_UIDS.key, studentUid)
            .orderBy(LessonField.DAY.key).orderBy(LessonField.START_HOUR.key).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    val lessonList = convertDocumentToLessonList(result.documents)
                    trySend(Resource.Success(lessonList))
                } else {
                    trySend(Resource.Error(message = task.exception?.message?.stringfy()))
                }
            }.await()
        awaitClose { this.cancel() }
    }

    fun getAllLessons(): Flow<Resource<List<Lesson>>> = callbackFlow {
        lessonCollectionRef.orderBy(LessonField.DAY.key).orderBy(LessonField.START_HOUR.key).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    val lessonList = convertDocumentToLessonList(result.documents)
                    trySend(Resource.Success(lessonList))
                } else {
                    trySend(Resource.Error(message = task.exception?.message?.stringfy()))
                }
            }.await()
        awaitClose { this.cancel() }
    }

    fun requestLesson(studentUid: String, lessonUid: String): Flow<Resource<Unit>> = callbackFlow {
        lessonCollectionRef.document(lessonUid)
            .update(LessonField.REQUEST_UIDS.key, FieldValue.arrayUnion(studentUid))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Resource.Success())
                } else {
                    trySend(Resource.Error(message = task.exception?.message?.stringfy()))
                }
            }.await()
        awaitClose { this.cancel() }
    }

}

typealias Uid = String
