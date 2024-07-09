package com.example.cumhuriyetsporsalonu.ui.auth.login

import android.util.Log
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


    fun login(email: String, password: String) {
        firebaseRepository.signIn(
            email, password
        ) { result ->
            when (result) {
                is Resource.Loading -> {
                    var count = 0
                    Log.d(TAG, "login: true ${count++}")
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(false)
                    sendAction(LoginActionBus.ShowError(result.message))
                }

                is Resource.Success -> {
                    var count = 1
                    Log.d(TAG, "login: false ${count++}")
                    setLoading(false)
                    result.data?.let {
                        userUid = it
                        sendAction(LoginActionBus.LoggedIn)
                    }
                }
            }
        }
    }
}
