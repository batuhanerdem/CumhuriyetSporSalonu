package com.example.cumhuriyetsporsalonu.ui.main.profile

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus

sealed interface ProfileActionBus : BaseActionBus {

    data object Init : ProfileActionBus

    data object Error : ProfileActionBus

    data object LessonsLoaded : ProfileActionBus

}