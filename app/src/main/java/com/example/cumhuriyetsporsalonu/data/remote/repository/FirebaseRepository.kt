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
        trySend(Resource.Loading())
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//                    Log.d("tag", "register: ${task.isSuccessful} ${task.result} ${task.exception} ") //crash yiyorum bu satirdan hayat cok guzel
                if (task.isSuccessful) {
                    val result = task.result
                    trySend(Resource.Success(result.user?.uid))
                } else {
                    Log.d("tag", "register: im here")
                    trySend(Resource.Error(message = task.exception?.message?.stringfy()))
                }
            }.await()
        } catch (e: Exception) {
            Log.d("tag", "exception: ${e.message}")
        }

        awaitClose { this.cancel() }

    }

    fun signIn(email: String, password: String): Flow<Resource<Uid>> = callbackFlow {
        trySend(Resource.Loading())
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
        trySend(Resource.Loading())
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
        trySend(Resource.Loading())
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
        trySend(Resource.Loading())
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
        trySend(Resource.Loading())
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
        trySend(Resource.Loading())
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
        trySend(Resource.Loading())
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

    fun deleteAllLessonsFromStudents() { // test
        userCollectionRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                result.documents.forEach {
                    Log.d("tag", "deleteAllLessonsFromStudents: $it")
                    it.reference.update(UserField.LESSON_UIDS.key, emptyList<String>())
                }
            }
        }
    }

}

typealias Uid = String
