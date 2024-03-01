package com.example.cumhuriyetsporsalonu.ui.auth.login

import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.Stringfy
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import com.example.newsapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<LoginActionBus>() {

    fun loginWithEmailAndPassword(email: String, password: String, name: String? = null) {
        if (email.isBlank() || password.isBlank()) return
        sendAction(LoginActionBus.Loading)
        firebaseRepository.signIn(
            email, password, ::signInCallBack
        )


    }

    private fun signInCallBack(result: Resource<String>) {
        when (result) {
            is Resource.Loading -> {}
            is Resource.Error -> sendAction(LoginActionBus.ShowError(result.message))

            is Resource.Success -> {
                result.data?.let {
                    sendAction(LoginActionBus.LoggedIn(it))
                }
            }
        }
    }
}
