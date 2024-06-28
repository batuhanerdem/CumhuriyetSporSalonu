package com.example.cumhuriyetsporsalonu.ui.main.profile.edit_profile

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus
import com.example.cumhuriyetsporsalonu.utils.Stringfy

sealed interface EditProfileActionBus : BaseActionBus {

    data object Init : EditProfileActionBus

    data class ShowError(val error: Stringfy? = null) : EditProfileActionBus

    data object UserUpdated : EditProfileActionBus

}