package com.example.cumhuriyetsporsalonu.ui.auth.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<LoginActionBus>() {

    var userUid: String? = null

    fun login(email: String, password: String) {
        firebaseRepository.signIn(email, password).onEach { result ->
            when (result) {
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    setLoading(false)
                    sendAction(LoginActionBus.ShowError(result.message))
                }

                is Resource.Success -> {
                    setLoading(false)
                    result.data?.let {
                        userUid = it
                        sendAction(LoginActionBus.LoggedIn)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}
