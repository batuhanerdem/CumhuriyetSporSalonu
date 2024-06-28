package com.example.cumhuriyetsporsalonu.ui.main.home

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus
import com.example.cumhuriyetsporsalonu.utils.Stringfy

sealed interface HomeActionBus : BaseActionBus {

    data object Init : HomeActionBus

    data class ShowError(val errorMessage: Stringfy? = null) : HomeActionBus

    data object Loading : HomeActionBus

    data object LessonsLoaded : HomeActionBus

}