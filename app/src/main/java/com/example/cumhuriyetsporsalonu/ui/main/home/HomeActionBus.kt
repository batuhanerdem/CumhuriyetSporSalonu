package com.example.cumhuriyetsporsalonu.ui.main.home

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus

sealed interface HomeActionBus : BaseActionBus {

    data object Init : HomeActionBus

    data class ShowErrorMessage(val errorMessage: String?) : HomeActionBus

    data object Loading : HomeActionBus

}