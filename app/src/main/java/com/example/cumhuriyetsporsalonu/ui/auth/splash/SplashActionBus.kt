package com.example.cumhuriyetsporsalonu.ui.auth.splash

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus

sealed interface SplashActionBus : BaseActionBus {

    data object Init : SplashActionBus

    data object ReadyToGo : SplashActionBus

    data object Loading : SplashActionBus

    data object NotVerified: SplashActionBus

    data object Error : SplashActionBus

}