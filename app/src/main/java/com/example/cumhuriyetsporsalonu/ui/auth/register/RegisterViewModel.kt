package com.example.cumhuriyetsporsalonu.ui.auth.register

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cumhuriyetsporsalonu.domain.use_case.RegisterUseCase
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterActionBus>() {

    var userUid: String? = null

    fun createAccount(
        email: String, password: String, name: String, surname: String
    ) {
        viewModelScope.launch {
            registerUseCase.execute(email, password, name, surname).collect { result ->
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
}