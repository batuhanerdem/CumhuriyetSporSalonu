package com.example.cumhuriyetsporsalonu.ui.auth

import com.example.newsapp.ui.base.BaseActionBus

sealed interface AuthActionBus : BaseActionBus {
    data object Init : AuthActionBus
}