package com.example.cumhuriyetsporsalonu.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _isLoggedInrSuccessful = MutableLiveData<Boolean>()
    val isLoggedInrSuccessful: LiveData<Boolean>
        get() = _isLoggedInrSuccessful

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun loginWithEmailAndPassword(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) return
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            _isLoggedInrSuccessful.value = true
        }.addOnFailureListener {
            println(it.message)
            _errorMessage.value = it.localizedMessage
        }
    }
}