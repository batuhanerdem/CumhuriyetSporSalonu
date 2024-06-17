package com.example.cumhuriyetsporsalonu.ui.auth

import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : BaseViewModel<AuthActionBus>() {
    var uid: String? = null
}