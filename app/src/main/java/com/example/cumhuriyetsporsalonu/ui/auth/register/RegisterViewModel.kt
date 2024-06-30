package com.example.cumhuriyetsporsalonu.ui.auth.register

import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.data.remote.repository.Uid
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.Stringfy
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<RegisterActionBus>() {

    var userUid: String? = null

    fun createAccountWithEmailPasswordName(
        email: String, password: String, name: String, surname: String
    ) {
        if (email.isBlank() || password.isBlank()) return
        sendAction(RegisterActionBus.Loading)
        firebaseRepository.register(email, password, name, surname, ::registerCallBack)
    }


    private fun registerCallBack(result: Resource<Uid>) {
        when (result) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                sendAction(RegisterActionBus.ShowError(result.message))
            }

            is Resource.Success -> {
                userUid = result.data ?: return
                sendAction(RegisterActionBus.RegisteredSuccessfully)
            }
        }

    }

}