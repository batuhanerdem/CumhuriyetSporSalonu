package com.example.cumhuriyetsporsalonu.data.remote.repository

import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.domain.model.firebase_collection.UserField
import com.example.cumhuriyetsporsalonu.domain.model.enums.CollectionName
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FirebaseRepository @Inject constructor(
    private val auth: FirebaseAuth, private val db: FirebaseFirestore
) {

    fun register(
        email: String, password: String, name: String? = null, callBack: (Resource<User>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnFailureListener {
            callBack(Resource.Error(message = it.message?.stringfy()))
        }.addOnSuccessListener {
            it.user?.let { user ->
                val myUser = User(user.uid, email, name ?: "no name")
                callBack(Resource.Success(myUser))
            }
        }


    }

    fun signIn(
        email: String, password: String, callBack: (Resource<String>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnFailureListener {
            callBack(Resource.Error(it.message?.stringfy()))
        }.addOnSuccessListener {
            it.user?.let { user ->
                callBack(Resource.Success(user.uid))
            }
        }
    }

    fun createUserForDB(user: User, callback: (isSuccess: Boolean) -> Unit) {
        val userHashMap = createUserHashMap(user)
        db.collection(CollectionName.USER.value).document(user.uid).set(userHashMap)
            .addOnCompleteListener {
                callback(it.isSuccessful)
            }
    }

    fun getUserByUid(uid: String, callback: (user: User?) -> Unit) {
        db.collection(CollectionName.USER.value).document(uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                if (result.get(UserField.UID.key) == null) {
                    callback(null)
                    return@addOnCompleteListener
                }
                val uid = result.get(UserField.UID.key) as String
                val name = result.get(UserField.NAME.key) as String
                val email = result.get(UserField.EMAIL.key) as String
                val isVerified = result.get(UserField.IS_VERIFIED.key) as Boolean
                val myUser = User(uid = uid, email = email, name = name, isVerified = isVerified)
                callback(myUser)
            }
        }
    }


    private fun createUserHashMap(user: User): HashMap<String, Any?> {
        return hashMapOf(
            UserField.UID.key to user.uid,
            UserField.NAME.key to user.name,
            UserField.EMAIL.key to user.email,
            UserField.IS_VERIFIED.key to user.isVerified
        )
    }
}