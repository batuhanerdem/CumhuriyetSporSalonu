package com.example.cumhuriyetsporsalonu.ui.auth.login

import androidx.lifecycle.viewModelScope
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<LoginActionBus>() {

    init {
        viewModelScope.launch {
//            firebaseRepository.deleteAllLessonsFromStudents()
        }
    }

    var userUid: String? = null

    fun login(email: String, password: String) {
        setLoading(true)
        firebaseRepository.signIn(email, password).onEach { result ->
            setLoading(false)
            when (result) {

                is Resource.Error -> {
                    sendAction(LoginActionBus.ShowError(result.message))
                }

                is Resource.Success -> {
                    result.data?.let {
                        userUid = it
                        sendAction(LoginActionBus.LoggedIn)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}
