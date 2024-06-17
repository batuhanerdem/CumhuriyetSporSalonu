package com.example.cumhuriyetsporsalonu.ui.auth.login

import com.example.cumhuriyetsporsalonu.utils.Stringfy
import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus

sealed interface LoginActionBus : BaseActionBus {

    data object Init : LoginActionBus

    data class LoggedIn(val uid: String) : LoginActionBus

    data class ShowError(val error: Stringfy? = null) : LoginActionBus

    data object Loading : LoginActionBus

}