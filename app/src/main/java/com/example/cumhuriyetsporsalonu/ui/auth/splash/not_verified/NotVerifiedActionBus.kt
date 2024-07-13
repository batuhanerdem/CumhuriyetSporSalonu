package com.example.cumhuriyetsporsalonu.ui.auth.splash.not_verified

import com.example.cumhuriyetsporsalonu.ui.auth.login.LoginActionBus
import com.example.cumhuriyetsporsalonu.ui.auth.splash.SplashActionBus
import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus
import com.example.cumhuriyetsporsalonu.utils.Stringfy

sealed interface NotVerifiedActionBus : BaseActionBus {

    data object Init : NotVerifiedActionBus

    data class ShowError(val error: Stringfy? = null) : NotVerifiedActionBus

    data object StatusLoaded : NotVerifiedActionBus

}