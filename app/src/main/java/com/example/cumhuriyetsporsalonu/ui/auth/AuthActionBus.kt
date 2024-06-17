package com.example.cumhuriyetsporsalonu.ui.auth

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus

sealed interface AuthActionBus : BaseActionBus {
    data object Init : AuthActionBus
}