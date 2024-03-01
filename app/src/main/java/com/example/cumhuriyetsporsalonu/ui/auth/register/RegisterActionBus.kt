package com.example.cumhuriyetsporsalonu.ui.auth.register

import com.example.newsapp.ui.base.BaseActionBus


sealed interface RegisterActionBus : BaseActionBus {

    data object Init : RegisterActionBus

    data object RegisteredSuccessFully : RegisterActionBus

    data object UserIsNotRegistered : RegisterActionBus

    data object Loading : RegisterActionBus

}