package com.example.cumhuriyetsporsalonu.ui.main.bmi

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus
import com.example.cumhuriyetsporsalonu.utils.Stringfy

sealed interface BMIActionBus : BaseActionBus {

    data object Init : BMIActionBus

    data class ShowError(val error: Stringfy? = null) : BMIActionBus

    data object BMICalculated : BMIActionBus

    data object BMISaved : BMIActionBus

}