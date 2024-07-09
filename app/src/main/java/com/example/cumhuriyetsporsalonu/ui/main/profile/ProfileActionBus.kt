package com.example.cumhuriyetsporsalonu.ui.main.profile

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus
import com.example.cumhuriyetsporsalonu.utils.Stringfy

sealed interface ProfileActionBus : BaseActionBus {

    data object Init : ProfileActionBus

    data class ShowError(val error: Stringfy? = null) : ProfileActionBus

    data object LessonsLoaded : ProfileActionBus

}