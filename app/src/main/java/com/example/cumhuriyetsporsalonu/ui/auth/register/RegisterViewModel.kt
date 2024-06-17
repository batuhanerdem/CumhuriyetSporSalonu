package com.example.cumhuriyetsporsalonu.ui.auth.register

import androidx.lifecycle.viewModelScope
import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.Stringfy
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<RegisterActionBus>() {

    private var user: User? = null

    fun createAccountWithEmailAndPassword(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) return
        sendAction(RegisterActionBus.Loading)
        viewModelScope.launch {
            firebaseRepository.register(email, password, null, ::registerCallBack)
            user?.let { user ->
                firebaseRepository.createUserForDB(user) {
                    if (it) {
                        UserUtils.setCurrentUser(user)
                        sendAction(RegisterActionBus.RegisteredSuccessFully)
                    } else {
                        sendAction(RegisterActionBus.UserIsNotRegistered)
                    }
                }
            }


        }
    }


    private fun registerCallBack(result: Resource<User>) {
        when (result) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                sendStringfyMessage(result.message)
            }

            is Resource.Success -> {
                val user = result.data
                sendAction(RegisterActionBus.RegisteredSuccessFully)
            }
        }

    }


    private fun sendStringfyMessage(stringfy: Stringfy?) {
        val message = stringfy ?: R.string.register_error_default.stringfy()
        sendMessage(message)
    }

}