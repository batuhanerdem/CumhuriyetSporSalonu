package com.example.cumhuriyetsporsalonu.ui.auth.register

import com.example.cumhuriyetsporsalonu.domain.use_case.RegisterUseCase
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterActionBus>() {

    var userUid: String? = null

    fun createAccount(
        email: String, password: String, name: String, surname: String
    ) {

        registerUseCase.execute(email, password, name, surname) { result ->
            when (result) {
                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(false)
                    sendAction(RegisterActionBus.ShowError(result.message))
                }

                is Resource.Success -> {
                    setLoading(false)
                    result.data?.let {
                        userUid = it
                        sendAction(RegisterActionBus.RegisteredSuccessfully)
                    }
                }

            }

        }
    }
}