package com.example.cumhuriyetsporsalonu.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth

    private val _isRegisterSuccessful = MutableLiveData<Boolean>()
    val isRegisterSuccessful: LiveData<Boolean>
        get() = _isRegisterSuccessful

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun createAccountWithEmailAndPassword(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) return
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // basarili
                _isRegisterSuccessful.value = true
            }.addOnFailureListener {
                // basarisiz
                _errorMessage.value = it.localizedMessage

            }
    }

}