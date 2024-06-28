package com.example.cumhuriyetsporsalonu.ui.auth.login

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<LoginActionBus>() {

    var userUid: String? = null


    fun loginWithEmailAndPassword(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) return
        sendAction(LoginActionBus.Loading)
        firebaseRepository.signIn(
            email, password
        ) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Error -> sendAction(LoginActionBus.ShowError(result.message))

                is Resource.Success -> {
                    result.data?.let {
                        userUid = it
                        sendAction(LoginActionBus.LoggedIn)
                    }
                }
            }
        }
    }
}
