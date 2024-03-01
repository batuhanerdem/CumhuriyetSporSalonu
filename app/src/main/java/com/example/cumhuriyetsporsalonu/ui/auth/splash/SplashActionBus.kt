package com.example.cumhuriyetsporsalonu.ui.auth.splash

import com.example.newsapp.ui.base.BaseActionBus

sealed interface SplashActionBus : BaseActionBus {

    data object Init : SplashActionBus

    data object ReadyToGo : SplashActionBus

    data object Loading : SplashActionBus

    data object Error : SplashActionBus

}