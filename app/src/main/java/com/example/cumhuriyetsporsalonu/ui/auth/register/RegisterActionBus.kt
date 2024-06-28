package com.example.cumhuriyetsporsalonu.ui.auth.register

import com.example.cumhuriyetsporsalonu.ui.auth.login.LoginActionBus
import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus
import com.example.cumhuriyetsporsalonu.utils.Stringfy


sealed interface RegisterActionBus : BaseActionBus {

    data object Init : RegisterActionBus

    data object RegisteredSuccessfully : RegisterActionBus

    data class ShowError(val error: Stringfy? = null) : RegisterActionBus

    data object Loading : RegisterActionBus

}