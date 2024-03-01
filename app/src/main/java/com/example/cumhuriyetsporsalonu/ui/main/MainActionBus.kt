package com.example.cumhuriyetsporsalonu.ui.main

import com.example.newsapp.ui.base.BaseActionBus

sealed interface MainActionBus : BaseActionBus {

    data object Init : MainActionBus

}