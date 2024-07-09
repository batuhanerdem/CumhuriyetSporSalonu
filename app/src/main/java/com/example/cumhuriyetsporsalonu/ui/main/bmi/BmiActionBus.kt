package com.example.cumhuriyetsporsalonu.ui.main.bmi

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus
import com.example.cumhuriyetsporsalonu.utils.Stringfy

sealed interface BmiActionBus : BaseActionBus {

    data object Init : BmiActionBus

    data class ShowError(val error: Stringfy? = null) : BmiActionBus

    data object BmiSaved : BmiActionBus

}