package com.example.cumhuriyetsporsalonu.ui.auth.register

import androidx.lifecycle.viewModelScope
import com.example.cumhuriyetsporsalonu.domain.use_case.RegisterUseCase
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterActionBus>() {

    var userUid: String? = null

    fun createAccount(
        email: String, password: String, name: String, surname: String
    ) {
        setLoading(true)
        registerUseCase.execute(email, password, name, surname).onEach { result ->
            setLoading(false)
            when (result) {
                is Resource.Error -> {
                    sendAction(RegisterActionBus.ShowError(result.message))
                }

                is Resource.Success -> {
                    result.data?.let {
                        userUid = it
                        sendAction(RegisterActionBus.RegisteredSuccessfully)
                    }
                }

            }
        }.launchIn(viewModelScope)
    }
}