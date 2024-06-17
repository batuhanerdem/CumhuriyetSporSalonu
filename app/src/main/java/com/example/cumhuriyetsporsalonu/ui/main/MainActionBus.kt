package com.example.cumhuriyetsporsalonu.ui.main

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus

sealed interface MainActionBus : BaseActionBus {

    data object Init : MainActionBus

}