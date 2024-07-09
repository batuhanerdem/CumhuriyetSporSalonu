package com.example.cumhuriyetsporsalonu.ui.auth.login

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus
import com.example.cumhuriyetsporsalonu.utils.Stringfy

sealed interface LoginActionBus : BaseActionBus {

    data object Init : LoginActionBus

    data object LoggedIn : LoginActionBus

    data class ShowError(val error: Stringfy? = null) : LoginActionBus

}